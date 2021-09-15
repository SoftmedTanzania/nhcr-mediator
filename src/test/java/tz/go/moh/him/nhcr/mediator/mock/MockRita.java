package tz.go.moh.him.nhcr.mediator.mock;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.openhim.mediator.engine.testing.MockHTTPConnector;
import tz.go.moh.him.nhcr.mediator.orchestrator.RitaClientsSearchOrchestratorTest;

import java.util.Collections;
import java.util.Map;

/**
 * Represents a mock destination.
 */
public class MockRita extends MockHTTPConnector {
    private boolean isRequestForToken;

    /**
     * Gets the response.
     *
     * @return Returns the response.
     */
    @Override
    public String getResponse() {
        try {
            if (isRequestForToken)
                return IOUtils.toString(RitaClientsSearchOrchestratorTest.class.getClassLoader().getResourceAsStream("rita_success_authentication_response.json"));
            else
                return IOUtils.toString(RitaClientsSearchOrchestratorTest.class.getClassLoader().getResourceAsStream("rita_success_response.json"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the status code.
     *
     * @return Returns the status code.
     */
    @Override
    public Integer getStatus() {
        return 200;
    }

    /**
     * Gets the HTTP headers.
     *
     * @return Returns the HTTP headers.
     */
    @Override
    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    /**
     * Handles the message.
     *
     * @param msg The message.
     */
    @Override
    public void executeOnReceive(MediatorHTTPRequest msg) {
        if (msg.getOrchestration().equalsIgnoreCase("Request for Authentication Token")) {
            Assert.assertTrue(msg.getUri().contains("/oauth/token"));
            isRequestForToken = true;
        } else {
            Assert.assertTrue(msg.getUri().contains("?pin=627931576839"));
            Assert.assertTrue(msg.getHeaders().get("Authorization").contains("asdda-ffaf-affafa-afffafaf"));
            isRequestForToken = false;
        }
    }
}
