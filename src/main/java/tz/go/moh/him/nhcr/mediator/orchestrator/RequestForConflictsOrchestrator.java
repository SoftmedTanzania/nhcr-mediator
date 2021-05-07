package tz.go.moh.him.nhcr.mediator.orchestrator;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.LazyConnection;
import ca.uhn.hl7v2.model.v231.message.ADR_A19;
import ca.uhn.hl7v2.model.v231.message.QRY_A19;
import ca.uhn.hl7v2.parser.Parser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpStatus;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.EmrRequestForConflictsMessage;
import tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils;
import tz.go.moh.him.nhcr.mediator.utils.MllpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents the Request For Conflicts orchestrator.
 */
public class RequestForConflictsOrchestrator extends BaseOrchestrator {
    /**
     * The Gson Instance used for serialization and deserialization of jsons
     */
    public Gson gson;

    /**
     * Initializes a new instance of the {@link RequestForConflictsOrchestrator} class.
     *
     * @param config The configuration.
     */
    public RequestForConflictsOrchestrator(MediatorConfig config) {
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
        List<Client> conflicts = new ArrayList<>();

        EmrRequestForConflictsMessage message = gson.fromJson(request.getBody(), EmrRequestForConflictsMessage.class);

        // Create a HapiContext
        HapiContext context = new DefaultHapiContext();

        // Create a Parser
        Parser parser = context.getPipeParser();

        // Create a connection
        LazyConnection conn = null;

        // Get the security token
        String securityToken = request.getHeaders().get("x-nhcr-token");
        if (securityToken == null) {
            request.getRequestHandler().tell(new FinishRequest(errorMessageResource.getString("ERROR_TOKEN_IS_BLANK"), "text/plain", HttpStatus.SC_BAD_REQUEST), getSelf());
            return;
        }

        // Check the sending application
        if (message.getSendingApplication() == null) {
            request.getRequestHandler().tell(new FinishRequest(errorMessageResource.getString("ERROR_SENDING_APPLICATION_IS_BLANK"), "text/plain", HttpStatus.SC_BAD_REQUEST), getSelf());
            return;
        }

        // Check the facility HFR code
        if (message.getFacilityHfrCode() == null) {
            request.getRequestHandler().tell(new FinishRequest(errorMessageResource.getString("ERROR_FACILITY_HFR_CODE_IS_BLANK"), "text/plain", HttpStatus.SC_BAD_REQUEST), getSelf());
            return;
        }

        // Prepare and send the query
        QRY_A19 query = HL7v2MessageBuilderUtils.createQryA19(message.getSendingApplication(), message.getFacilityHfrCode(), "NHCR", "NHCR", securityToken, String.valueOf(UUID.randomUUID()), new Date(), "", "", "CONFLICTS", message.getStartDateTime(), message.getEndDateTime(), message.getOffset(), message.getLimit());
        String response = MllpUtils.sendMessage(query, config, context, conn);

        // Check if a response was received
        if (response == null) {
            // error getting a response from the NHCR
            request.getRequestHandler().tell(new FinishRequest(errorMessageResource.getString("ERROR_NO_RESPONSE_FROM_TARGET_SYSTEM"), "text/plain", HttpStatus.SC_INTERNAL_SERVER_ERROR), getSelf());
            return;
        }

        // Check the acknowledgement
        ADR_A19 adr = (ADR_A19) parser.parse(response.replace("|2.5", "|2.3.1"));
        if (!adr.getMSA().getAcknowledgementCode().getValue().equals("AA")) {
            String error = adr.getERR().getErr1_ErrorCodeAndLocation(0).getCodeIdentifyingError().getText().getValue() + ". " + adr.getERR().getErr1_ErrorCodeAndLocation(0).getCodeIdentifyingError().getAlternateText().getValue();
            request.getRequestHandler().tell(new FinishRequest(error, "text/plain", HttpStatus.SC_BAD_REQUEST), getSelf());
            return;
        }

        // Parse the response and build the client search response clientSearchResponse
        conflicts = HL7v2MessageBuilderUtils.parseAdrA19Message(response);

        if (conflicts.size() > 0) {
            request.getRequestHandler().tell(new FinishRequest(gson.toJson(conflicts), "text/json", HttpStatus.SC_OK), getSelf());
        } else {
            request.getRequestHandler().tell(new FinishRequest(gson.toJson(conflicts), "text/json", HttpStatus.SC_NOT_FOUND), getSelf());
        }
    }
}
