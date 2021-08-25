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

    /**
     * Initializes a new instance of the {@link MockRita} class.
     */
    public MockRita() {
    }

    /**
     * Gets the response.
     *
     * @return Returns the response.
     */
    @Override
    public String getResponse() {
        try {
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
        Assert.assertTrue(msg.getUri().contains("?pin=627931576839"));
    }
}
