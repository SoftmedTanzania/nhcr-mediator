package tz.go.moh.him.nhcr.mediator.orchestrator;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
 * Represents a RITA Authentication orchestrator.
 */
public class RitaAuthenticationActor extends BaseOrchestrator {
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
     * Initializes a new instance of the {@link RitaAuthenticationActor} class.
     *
     * @param config The configuration.
     */
    public RitaAuthenticationActor(MediatorConfig config) {
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

        Map<String, String> headers = new HashMap<>();

        headers.put(HttpHeaders.CONTENT_TYPE, "multipart/form-data");

        String host;
        int port;
        String authenticationPath;
        String scheme;
        String username;
        String password;

        if (config.getDynamicConfig().isEmpty()) {
            log.debug("Dynamic config is empty, using config from mediator.properties");

            host = config.getProperty("rita.host");
            port = Integer.parseInt(config.getProperty("rita.port"));
            authenticationPath = config.getProperty("rita.authenticationPath");
            scheme = config.getProperty("rita.scheme");
        } else {
            log.debug("Using dynamic config");

            JSONObject destinationProperties = new JSONObject(config.getDynamicConfig()).getJSONObject("ritaConnectionProperties");

            host = destinationProperties.getString("ritaHost");
            port = destinationProperties.getInt("ritaPort");
            authenticationPath = config.getProperty("rita.authenticationPath");
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

        //Requesting for authentication token from RITA
        host = scheme + "://" + host + ":" + port + authenticationPath;
        List<Pair<String, String>> parameters = new ArrayList<>();
        parameters.add(new ImmutablePair<>("grant_type", "client_credentials"));

        MediatorHTTPRequest ritaAuthenticationRequest = new MediatorHTTPRequest(request.getRequestHandler(), getSelf(), "Request for Authentication Token", "POST",
                host, null, headers, parameters);

        //Initialize http connector
        ActorSelection httpConnector = getContext().actorSelection(config.userPathFor("http-connector"));
        httpConnector.tell(ritaAuthenticationRequest, getSelf());
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
            RitaAuthenticationResponse ritaAuthenticationResponse = serializer.deserialize(((MediatorHTTPResponse) message).getBody(), RitaAuthenticationResponse.class);
            ActorRef actor = getContext().actorOf(Props.create(RitaActor.class, config, ritaAuthenticationResponse));
            actor.tell(workingRequest, getSelf());
        } else {
            unhandled(message);
        }
    }
}
