package tz.go.moh.him.nhcr.mediator.orchestrator;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpStatus;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.EmrClientsRegistrationAndUpdatesMessage;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZXT_A01;
import tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils;
import tz.go.moh.him.nhcr.mediator.utils.MllpUtils;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateDeserializer;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateSerializer;

import java.util.Date;

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
                    emrClientsRegistrationAndUpdatesMessage.getOid(),
                    "NHCR",
                    "NHCR",
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvbmhjci5yYXN4cC5jb206ODA4MFwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYxMjkzMTk3NiwiZXhwIjoxNjEyOTM1NTc2LCJuYmYiOjE2MTI5MzE5NzYsImp0aSI6IlVDb21HNVpQVlN1Wk9KMFgiLCJzdWIiOjEsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.UzmHbKcgrgIFdxRGfs74Oyb1C1lvgigk5IDcCvePLis",
                    "1",
                    recordedDate,
                    client
            );

            MllpUtils.sendMessage(zxtA01, config, context, conn);
        }

        request.getRequestHandler().tell(new FinishRequest("Success", "text/plain", HttpStatus.SC_OK), getSelf());
    }
}
