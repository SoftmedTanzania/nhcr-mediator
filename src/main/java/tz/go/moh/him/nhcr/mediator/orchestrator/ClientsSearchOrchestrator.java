package tz.go.moh.him.nhcr.mediator.orchestrator;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.LazyConnection;
import ca.uhn.hl7v2.model.v231.message.QRY_A19;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpStatus;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.EmrClientsSearchMessage;
import tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils;
import tz.go.moh.him.nhcr.mediator.utils.MllpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Clients Search orchestrator.
 */
public class ClientsSearchOrchestrator extends BaseOrchestrator {
    /**
     * The Gson Instance used for serialization and deserialization of jsons
     */
    public Gson gson;

    /**
     * Initializes a new instance of the {@link ClientsSearchOrchestrator} class.
     *
     * @param config The configuration.
     */
    public ClientsSearchOrchestrator(MediatorConfig config) {
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
        List<Client> clients = new ArrayList<>();

        EmrClientsSearchMessage message = gson.fromJson(request.getBody(), EmrClientsSearchMessage.class);

        // Create a HapiContext
        HapiContext context = new DefaultHapiContext();

        // Create a connection
        LazyConnection conn = null;

        // Get the security token
        String securityToken = request.getHeaders().get("x-nhcr-token");
        if (securityToken == null) {
            request.getRequestHandler().tell(new FinishRequest(errorMessageResource.getString("ERROR_TOKEN_IS_BLANK"), "text/plain", HttpStatus.SC_BAD_REQUEST), getSelf());
            return;
        }

        // Prepare and send the query
        QRY_A19 query = HL7v2MessageBuilderUtils.createQryA19(message.getSendingApplication(), message.getFacilityHfrCode(), "NHCR", "NHCR", securityToken, String.valueOf(UUID.randomUUID()), new Date(), message.getId(), message.getType(), "PATIENTS", "", "");
        String response = MllpUtils.sendMessage(query, config, context, conn);

        if (response != null) {
            // Parse the response and build the client search response clientSearchResponse
            clients = HL7v2MessageBuilderUtils.parseAdrA19Message(response);
        }

        request.getRequestHandler().tell(new FinishRequest(gson.toJson(clients), "text/json", HttpStatus.SC_OK), getSelf());
    }
}