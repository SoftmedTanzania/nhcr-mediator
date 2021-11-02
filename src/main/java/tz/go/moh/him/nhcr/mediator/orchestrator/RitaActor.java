package tz.go.moh.him.nhcr.mediator.orchestrator;

import akka.actor.ActorSelection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.ClientId;
import tz.go.moh.him.nhcr.mediator.domain.ClientLinkage;
import tz.go.moh.him.nhcr.mediator.domain.EmrClientsSearchMessage;
import tz.go.moh.him.nhcr.mediator.domain.RitaResponse;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils.*;

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
        String username;
        String password;

        if (config.getDynamicConfig().isEmpty()) {
            log.debug("Dynamic config is empty, using config from mediator.properties");

            host = config.getProperty("rita.host");
            port = Integer.parseInt(config.getProperty("rita.port"));
            path = config.getProperty("rita.path");
            scheme = config.getProperty("rita.scheme");
        } else {
            log.debug("Using dynamic config");

            JSONObject destinationProperties = new JSONObject(config.getDynamicConfig()).getJSONObject("destinationConnectionProperties");

            host = destinationProperties.getString("ritaHost");
            port = destinationProperties.getInt("ritaPort");
            path = destinationProperties.getString("ritaPath");
            scheme = destinationProperties.getString("ritaScheme");

            if (destinationProperties.has("ritaUsername") && destinationProperties.has("ritaPassword")) {
                username = destinationProperties.getString("ritaUsername");
                password = destinationProperties.getString("ritaPassword");

                // if we have a username and a password
                // we want to add the username and password as the Basic Auth header in the HTTP request
                if (username != null && !"".equals(username) && password != null && !"".equals(password)) {
                    String auth = username + ":" + password;
                    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
                    String authHeader = "Basic " + new String(encodedAuth);
                    headers.put(HttpHeaders.AUTHORIZATION, authHeader);
                }
            }
        }

        host = scheme + "://" + host + ":" + port + path + "?pin=" + message.getId();

        MediatorHTTPRequest ritaRequest = new MediatorHTTPRequest(request.getRequestHandler(), getSelf(), host, "GET",
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
            this.onReceiveRequestInternal((MediatorHTTPRequest) message);
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

        if (ritaResponse.getPin() != null) {
            ClientId uln = new ClientId();
            uln.setId(String.valueOf(ritaResponse.getPin()));
            uln.setType(ULN);

            clientIds.add(uln);
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
            motherLinkage.setSourceOfId(ULN);
            motherLinkage.setTypeOfLinkage("Mother");
            client.setFamilyLinkages(Arrays.asList(motherLinkage));
        }

        if (ritaResponse.getFatherPin() != null) {
            ClientLinkage fatherLinkage = new ClientLinkage();
            fatherLinkage.setId(ritaResponse.getFatherPin());
            fatherLinkage.setSourceOfId(ULN);
            fatherLinkage.setTypeOfLinkage("Father");
            client.setOtherLinkages(Arrays.asList(fatherLinkage));
        }

        return client;
    }
}
