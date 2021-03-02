package tz.go.moh.him.nhcr.mediator.mllpTcpClient;

import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import org.json.JSONObject;
import org.openhim.mediator.engine.MediatorConfig;

import java.io.IOException;

public class MLLPBasedTCPClient {

    public static String sendMessage(String message, MediatorConfig config, HapiContext context, Connection conn) throws IOException {
        String host;
        int portNumber;

        if (config.getDynamicConfig().isEmpty()) {
            host = config.getProperty("destination.host");
            portNumber = Integer.parseInt(config.getProperty("destination.port"));
        } else {
            JSONObject connectionProperties = new JSONObject(config.getDynamicConfig()).getJSONObject("destinationConnectionProperties");

            host = connectionProperties.getString("destinationHost");
            portNumber = connectionProperties.getInt("destinationPort");
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
            boolean useTls = false;
            int port = 8888;
            conn = context.newClient("localhost", port, useTls);
        }

        try {
            Message next = iter.next();
            Message response = conn.getInitiator().sendAndReceive(next);
            System.out.println("Sent message. Response was " + response.encode());
        } catch (IOException e) {
            System.out.println("Didn't send out this message!");
            e.printStackTrace();

            // Since we failed, close the connection
            conn.close();
            conn = null;

        }

        return response;
    }
}