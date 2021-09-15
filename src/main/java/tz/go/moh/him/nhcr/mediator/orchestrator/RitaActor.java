package tz.go.moh.him.nhcr.mediator.orchestrator;

import akka.actor.ActorSelection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPResponse;
import tz.go.moh.him.mediator.core.serialization.JsonSerializer;
import tz.go.moh.him.nhcr.mediator.domain.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils.NATIONAL_ID;
import static tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils.VOTERS_ID;

/**
 * Represents a RITA Clients Search orchestrator.
 */
public class RitaActor extends BaseOrchestrator {
    /**
     * The serializer.
     */
    private static final JsonSerializer serializer = new JsonSerializer();
    /**
     * The Gson Instance used for serialization and deserialization of jsons
     */
    public Gson gson;
    /**
     * The Rita Authentication Response.
     */
    private RitaAuthenticationResponse ritaAuthenticationResponse;
    /**
     * The working request.
     */
    private MediatorHTTPRequest workingRequest;

    /**
     * Initializes a new instance of the {@link RitaActor} class.
     *
     * @param config The configuration.
     */
    public RitaActor(MediatorConfig config) {
        super(config);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    /**
     * Handles the received message.
     *
     * @param request The request.
     * @throws Exception if an exception occurs.
     */
    @Override
    protected void onReceiveRequestInternal(MediatorHTTPRequest request) throws Exception {
        EmrClientsSearchMessage message = gson.fromJson(request.getBody(), EmrClientsSearchMessage.class);

        log.info("Received request: " + request.getHost() + " " + request.getMethod() + " " + request.getPath());

        Map<String, String> headers = new HashMap<>();

        headers.put(HttpHeaders.CONTENT_TYPE, "application/json");

        List<Pair<String, String>> parameters = new ArrayList<>();

        String host;
        int port;
        String path;
        String scheme;

        if (config.getDynamicConfig().isEmpty()) {
            log.debug("Dynamic config is empty, using config from mediator.properties");

            host = config.getProperty("rita.host");
            port = Integer.parseInt(config.getProperty("rita.port"));
            path = config.getProperty("rita.personDetailsPath");
            scheme = config.getProperty("rita.scheme");
        } else {
            log.debug("Using dynamic config");

            JSONObject destinationProperties = new JSONObject(config.getDynamicConfig()).getJSONObject("ritaConnectionProperties");

            host = destinationProperties.getString("ritaHost");
            port = destinationProperties.getInt("ritaPort");
            path = destinationProperties.getString("ritaPath");
            scheme = destinationProperties.getString("ritaScheme");
        }

        if (ritaAuthenticationResponse != null && ritaAuthenticationResponse.getAccessToken() != null) {
            String authHeader = "Bearer " + ritaAuthenticationResponse.getAccessToken();
            headers.put(HttpHeaders.AUTHORIZATION, authHeader);
        }

        host = scheme + "://" + host + ":" + port + path + "?pin=" + message.getId();

        MediatorHTTPRequest ritaRequest = new MediatorHTTPRequest(request.getRequestHandler(), getSelf(), "Get Person Details from RITA", "GET",
                host, null, headers, parameters);

        ActorSelection httpConnector = getContext().actorSelection(config.userPathFor("http-connector"));
        httpConnector.tell(ritaRequest, getSelf());
    }

    /**
     * Handles the received message.
     *
     * @param message The received message.
     * @throws Exception if an exception occurs.
     */
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MediatorHTTPRequest) {
            workingRequest = (MediatorHTTPRequest) message;
            ritaAuthenticationResponse = getRitaAuthenticationToken();

            if (ritaAuthenticationResponse == null || ritaAuthenticationResponse.getError() == null) {
                this.onReceiveRequestInternal((MediatorHTTPRequest) message);
            } else {
                workingRequest.getRequestHandler().tell(new FinishRequest(gson.toJson(ritaAuthenticationResponse), "text/json", HttpStatus.SC_INTERNAL_SERVER_ERROR), getSelf());
            }
        } else if (message instanceof MediatorHTTPResponse) {
            RitaResponse ritaResponse = serializer.deserialize(((MediatorHTTPResponse) message).getBody(), RitaResponse.class);
            workingRequest.getRequestHandler().tell(new FinishRequest(gson.toJson(convertToClient(ritaResponse)), "text/json", HttpStatus.SC_OK), getSelf());
        } else {
            unhandled(message);
        }
    }

    private Client convertToClient(RitaResponse ritaResponse) {
        Client client = new Client();

        client.setFirstName(ritaResponse.getFirstName());
        client.setMiddleName(ritaResponse.getMiddleName());
        client.setLastName(ritaResponse.getLastName());
        client.setUln(String.valueOf(ritaResponse.getPin()));

        if (ritaResponse.getSex().equalsIgnoreCase("F"))
            client.setSex("Female");
        else
            client.setSex("Male");
        client.setDob(ritaResponse.getDateOfBirth());

        client.setDeathStatus(!ritaResponse.getPersonStatus().equalsIgnoreCase("ALIVE"));


        List<ClientId> clientIds = new ArrayList<>();
        if (ritaResponse.getNin() != null) {
            ClientId nationalId = new ClientId();
            nationalId.setId(String.valueOf(ritaResponse.getNin()));
            nationalId.setType(NATIONAL_ID);

            clientIds.add(nationalId);
        }

        if (ritaResponse.getVoterId() != null) {
            ClientId voterId = new ClientId();
            voterId.setId(String.valueOf(ritaResponse.getVoterId()));
            voterId.setType(VOTERS_ID);

            clientIds.add(voterId);
        }

        client.setIds(clientIds);

        if (ritaResponse.getMotherPin() != null) {
            ClientLinkage motherLinkage = new ClientLinkage();
            motherLinkage.setId(ritaResponse.getMotherPin());
            motherLinkage.setSourceOfId("ULN");
            motherLinkage.setTypeOfLinkage("Mother");
            client.setFamilyLinkages(motherLinkage);
        }

        if (ritaResponse.getFatherPin() != null) {
            ClientLinkage fatherLinkage = new ClientLinkage();
            fatherLinkage.setId(ritaResponse.getFatherPin());
            fatherLinkage.setSourceOfId("ULN");
            fatherLinkage.setTypeOfLinkage("Father");
            client.setOtherLinkages(fatherLinkage);
        }

        return client;
    }

    private RitaAuthenticationResponse getRitaAuthenticationToken() {
        log.info("Requesting Authentication Token from RITA");

        String host = "";
        String authenticationPath = "";
        String scheme = "";
        int port = 8081;
        String username;
        String password;
        String authHeader = "";

        if (!config.getDynamicConfig().isEmpty()) {
            log.info("Using dynamic config");

            JSONObject ritaProperties = new JSONObject(config.getDynamicConfig()).getJSONObject("ritaConnectionProperties");
            host = ritaProperties.getString("ritaHost");
            port = ritaProperties.getInt("ritaAuthenticationPort");
            authenticationPath = config.getProperty("rita.authenticationPath");
            scheme = ritaProperties.getString("ritaScheme");

            Map<String, String> headers = new HashMap<>();
            headers.put(HttpHeaders.CONTENT_TYPE, "multipart/form-data");

            if (ritaProperties.has("ritaUsername") && ritaProperties.has("ritaPassword")) {
                username = ritaProperties.getString("ritaUsername");
                password = ritaProperties.getString("ritaPassword");

                // if we have a username and a password
                // we want to add the username and password as the Basic Auth header in the HTTP request
                if (username != null && !"".equals(username) && password != null && !"".equals(password)) {
                    String auth = username + ":" + password;
                    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
                    authHeader = "Basic " + new String(encodedAuth);
                }
            }

            host = scheme + "://" + host + ":" + port + authenticationPath;
            try {
                //Requesting for authentication token from RITA
                HttpResponse<JsonNode> response = Unirest.post(host)
                        .header("Authorization", authHeader)
                        .field("grant_type", "client_credentials")
                        .asJson();


                log.info("Received authentication results from RITA = " + response.getBody().getObject().toString());
                return gson.fromJson(response.getBody().getObject().toString(), RitaAuthenticationResponse.class);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }


    }
}
