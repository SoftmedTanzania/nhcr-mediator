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
import tz.go.moh.him.nhcr.mediator.domain.EmrClientsRegistrationAndUpdatesMessage;
import tz.go.moh.him.nhcr.mediator.domain.EmrResponse;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZXT_A01;
import tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils;
import tz.go.moh.him.nhcr.mediator.utils.MllpUtils;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateDeserializer;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an NHCR orchestrator.
 */
public class ClientRegistrationAndUpdatesOrchestrator extends BaseOrchestrator {
    /**
     * The Gson Instance used for serialization and deserialization of jsons
     */
    public Gson gson;

    /**
     * Initializes a new instance of the {@link ClientRegistrationAndUpdatesOrchestrator} class.
     *
     * @param config The configuration.
     */
    public ClientRegistrationAndUpdatesOrchestrator(MediatorConfig config) {
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
        EmrClientsRegistrationAndUpdatesMessage emrClientsRegistrationAndUpdatesMessage = gson.fromJson(request.getBody(), EmrClientsRegistrationAndUpdatesMessage.class);

        // Create a HapiContext
        HapiContext context = new DefaultHapiContext();

        // Create a connection
        Connection conn = null;

        // Create a Parser
        Parser parser = context.getPipeParser();

        List<EmrResponse.FailedClientsMrn> failedClientsMrns = new ArrayList<>();

        int totalNumberOfClients = emrClientsRegistrationAndUpdatesMessage.getClients().size();
        int numberOfFailed = 0;

        for (Client client : emrClientsRegistrationAndUpdatesMessage.getClients()) {
            String messageTriggerEvent;
            if (client.getPostOrUpdate().equals(Client.PostOrUpdate.POST)) {
                messageTriggerEvent = "A01";
            } else {
                messageTriggerEvent = "A08";
            }

            Date recordedDate = new Date();

            ZXT_A01 zxtA01 = HL7v2MessageBuilderUtils.createZxtA01(
                    messageTriggerEvent,
                    emrClientsRegistrationAndUpdatesMessage.getSendingApplication(),
                    emrClientsRegistrationAndUpdatesMessage.getFacilityHfrCode(),
                    "NHCR",
                    "NHCR",
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvbmhjci5yYXN4cC5jb206ODA4MFwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYxMjkzMTk3NiwiZXhwIjoxNjEyOTM1NTc2LCJuYmYiOjE2MTI5MzE5NzYsImp0aSI6IlVDb21HNVpQVlN1Wk9KMFgiLCJzdWIiOjEsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.UzmHbKcgrgIFdxRGfs74Oyb1C1lvgigk5IDcCvePLis",
                    "1",
                    recordedDate,
                    client
            );

            String response = MllpUtils.sendMessage(zxtA01, config, context, conn);

            if (response != null) {
                ACK ack = (ACK) parser.parse(response);
                if (!ack.getMSA().getAcknowledgementCode().getValue().equals("AA")) {
                    EmrResponse.FailedClientsMrn failedClientsMrn = new EmrResponse.FailedClientsMrn();
                    failedClientsMrn.setMrn(client.getMrn());
                    failedClientsMrn.setError(ack.getMSA().getTextMessage().getValue());

                    failedClientsMrns.add(failedClientsMrn);
                    numberOfFailed++;
                }
            }
        }

        EmrResponse.Summary summary = new EmrResponse.Summary(totalNumberOfClients, totalNumberOfClients - numberOfFailed, numberOfFailed);

        EmrResponse emrResponse = new EmrResponse();
        emrResponse.setSummary(summary);

        int httpStatusCode = HttpStatus.SC_OK;
        if (failedClientsMrns.size() > 0) {
            emrResponse.setFailedClientsMrns(failedClientsMrns);
            httpStatusCode = HttpStatus.SC_BAD_REQUEST;
        }

        request.getRequestHandler().tell(new FinishRequest(gson.toJson(emrResponse), "text/plain", httpStatusCode), getSelf());
    }
}
