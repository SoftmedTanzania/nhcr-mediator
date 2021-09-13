package tz.go.moh.him.nhcr.mediator.utils;

import akka.actor.ActorRef;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import org.json.JSONObject;
import org.openhim.mediator.engine.CoreResponse;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.AddOrchestrationToCoreResponse;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MllpUtils {

    public static String sendMessage(MediatorHTTPRequest mediatorHTTPRequest, Message message, MediatorConfig config, HapiContext context, Connection conn, ActorRef actorRef) throws HL7Exception {
        String responseMessage = null;
        String host;
        int portNumber;
        boolean useTls;

        if (config.getDynamicConfig().isEmpty()) {
            host = config.getProperty("destination.host");
            portNumber = Integer.parseInt(config.getProperty("destination.port"));
            useTls = !config.getProperty("destination.scheme").equalsIgnoreCase("llp");
        } else {
            JSONObject connectionProperties = new JSONObject(config.getDynamicConfig()).getJSONObject("destinationConnectionProperties");

            host = connectionProperties.getString("destinationHost");
            portNumber = connectionProperties.getInt("destinationPort");
            useTls = !connectionProperties.getString("destinationScheme").equalsIgnoreCase("llp");
        }

        /* If we don't already have a connection, create one.
         * Note that unless something goes wrong, it's very common
         * to keep reusing the same connection until we are done
         * sending messages. Many systems keep a connection open
         * even if a long period will pass between messages being
         * sent. This is good practice, as it is much faster than
         * creating a new connection each time.
         */
        if (conn == null) {
            conn = context.newLazyClient(host, portNumber, useTls);
            conn.getInitiator().setTimeout(5, TimeUnit.MINUTES);
        }

        try {
            Message response = conn.getInitiator().sendAndReceive(message);
            responseMessage = response.encode();

            HashMap<String, String> header = new HashMap<>();
            header.put("content-type","text/plain;charset=UTF-8");
            header.put("accept","*/*");
            header.put("vary","Accept-Encoding");
            header.put("x-openhim-transactionid",mediatorHTTPRequest.getHeaders().get("x-openhim-transactionid"));

            CoreResponse.Request request = new CoreResponse.Request();
            request.setBody(message.encode());
            request.setHeaders(header);
            request.setHost(host);
            request.setMethod("TCP");
            request.setPort(String.valueOf(portNumber));
            request.setTimestamp(Calendar.getInstance().getTime());


            CoreResponse.Orchestration sendingDataToNHCROrchestration = new CoreResponse.Orchestration();
            sendingDataToNHCROrchestration.setRequest(request);
            sendingDataToNHCROrchestration.setName("Sending HL7 Messages to NHCR");

            CoreResponse.Response responseFromNHCR = new CoreResponse.Response();

            responseFromNHCR.setHeaders(header);
            responseFromNHCR.setBody(responseMessage);
            responseFromNHCR.setStatus(200);
            responseFromNHCR.setTimestamp(Calendar.getInstance().getTime());
            sendingDataToNHCROrchestration.setResponse(responseFromNHCR);

            /**
             * Sending an orchestration back to HIM
             */
            mediatorHTTPRequest.getRequestHandler().tell(new AddOrchestrationToCoreResponse(sendingDataToNHCROrchestration), actorRef);

            System.out.println("Sent message. Response was " + responseMessage);
        } catch (IOException | LLPException e) {
            System.out.println("Didn't send out this message!");
            e.printStackTrace();

            // Since we failed, close the connection
            conn.close();
            conn = null;

        }
        return responseMessage;
    }
}