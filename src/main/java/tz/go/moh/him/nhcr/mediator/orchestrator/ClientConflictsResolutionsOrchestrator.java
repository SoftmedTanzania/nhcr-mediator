package tz.go.moh.him.nhcr.mediator.orchestrator;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.model.v231.message.ACK;
import ca.uhn.hl7v2.parser.Parser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpStatus;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.ClientConflictResolutions;
import tz.go.moh.him.nhcr.mediator.domain.EmrClientsConflictsResolutionsMessage;
import tz.go.moh.him.nhcr.mediator.domain.EmrResponse;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZXT_A40;
import tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils;
import tz.go.moh.him.nhcr.mediator.utils.MllpUtils;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateDeserializer;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents an Clients Conflicts Resolutions NHCR orchestrator.
 */
public class ClientConflictsResolutionsOrchestrator extends BaseOrchestrator {
    /**
     * The Gson Instance used for serialization and deserialization of jsons
     */
    public Gson gson;

    /**
     * Initializes a new instance of the {@link ClientConflictsResolutionsOrchestrator} class.
     *
     * @param config The configuration.
     */
    public ClientConflictsResolutionsOrchestrator(MediatorConfig config) {
        super(config);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Client.PostOrUpdate.class, new AttributePostOrUpdateSerializer());
        gsonBuilder.registerTypeAdapter(Client.PostOrUpdate.class, new AttributePostOrUpdateDeserializer());
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
        EmrClientsConflictsResolutionsMessage emrClientsConflictsResolutionsMessage = gson.fromJson(request.getBody(), EmrClientsConflictsResolutionsMessage.class);

        // Create a HapiContext
        HapiContext context = new DefaultHapiContext();

        // Create a Parser
        Parser parser = context.getPipeParser();

        // Create a connection
        Connection conn = null;

        String securityToken = request.getHeaders().get("x-nhcr-token");
        List<EmrResponse.FailedClientsMrn> failedClientsMrns = new ArrayList<>();

        if (securityToken == null) {
            request.getRequestHandler().tell(new FinishRequest(errorMessageResource.getString("ERROR_TOKEN_IS_BLANK"), "text/plain", HttpStatus.SC_BAD_REQUEST), getSelf());
            return;
        }

        int numberOfFailed = 0;
        int totalNumberOfClients = emrClientsConflictsResolutionsMessage.getClients().size();


        for (ClientConflictResolutions resolvedConflicts : emrClientsConflictsResolutionsMessage.getClients()) {

            Date recordedDate = new Date();
            ZXT_A40 zxtA40 = HL7v2MessageBuilderUtils.createZxtA40(
                    emrClientsConflictsResolutionsMessage.getSendingApplication(),
                    emrClientsConflictsResolutionsMessage.getFacilityHfrCode(),
                    "NHCR",
                    "NHCR",
                    request.getHeaders().get("x-nhcr-token"),
                    String.valueOf(UUID.randomUUID()),
                    recordedDate,
                    resolvedConflicts
            );

            String response = MllpUtils.sendMessage(zxtA40, config, context, conn);

            if (response != null) {
                ACK ack = (ACK) parser.parse(response);
                if (!ack.getMSA().getAcknowledgementCode().getValue().equals("AA")) {
                    EmrResponse.FailedClientsMrn failedClientsMrn = new EmrResponse.FailedClientsMrn();
                    failedClientsMrn.setMrn(resolvedConflicts.getMrn());
                    failedClientsMrn.setError(
                            ack.getERR().getErr1_ErrorCodeAndLocation(0).getCodeIdentifyingError().getText().getValue() + ". " + ack.getERR().getErr1_ErrorCodeAndLocation(0).getCodeIdentifyingError().getAlternateText().getValue()
                    );
                    failedClientsMrns.add(failedClientsMrn);
                    numberOfFailed++;
                }
            }
        }

        EmrResponse emrResponse = new EmrResponse();
        emrResponse.setSummary(new EmrResponse.Summary(totalNumberOfClients, totalNumberOfClients - numberOfFailed, numberOfFailed));

        int httpStatusCode = HttpStatus.SC_OK;
        if (failedClientsMrns.size() > 0) {
            emrResponse.setFailedClientsMrns(failedClientsMrns);
            httpStatusCode = HttpStatus.SC_BAD_REQUEST;
        }

        request.getRequestHandler().tell(new FinishRequest(gson.toJson(emrResponse), "text/json", httpStatusCode), getSelf());
    }
}
