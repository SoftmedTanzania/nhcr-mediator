package tz.go.moh.him.nhcr.mediator.orchestrator;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import tz.go.moh.him.nhcr.mediator.utils.MllpUtils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Contains tests for the {@link ClientConflictsResolutionsOrchestrator} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MllpUtils.class)
public class ClientConflictsResolutionsOrchestratorTest extends BaseOrchestratorTest {

    /**
     * The orchestrator.
     */
    private final ActorRef orchestrator = system.actorOf(Props.create(ClientConflictsResolutionsOrchestrator.class, configuration));

    /**
     * Tests the mediator with a request with missing token headers.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testWithMissingTokenRequest() throws Exception {
        assertNotNull(system);
        new JavaTestKit(system) {{
            PowerMockito.mockStatic(MllpUtils.class);

            InputStream stream = ClientConflictsResolutionsOrchestratorTest.class.getClassLoader().getResourceAsStream("clients_conflicts_resolution.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/nhcr-conflicts-resolution",
                    IOUtils.toString(stream),
                    Collections.singletonMap("Content-Type", "application/json"),
                    Collections.emptyList()
            );

            orchestrator.tell(request, getRef());

            final Object[] out =
                    new ReceiveWhile<Object>(Object.class, duration("3 seconds")) {
                        @Override
                        protected Object match(Object msg) throws Exception {
                            if (msg instanceof FinishRequest) {
                                return msg;
                            }
                            throw noMatch();
                        }
                    }.get();

            String responseMessage = "";
            int responseStatus = 0;

            for (Object o : out) {
                if (o instanceof FinishRequest) {
                    responseStatus = ((FinishRequest) o).getResponseStatus();
                    responseMessage = ((FinishRequest) o).getResponse();
                    break;
                }
            }

            assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            assertEquals(400, responseStatus);
            assertTrue(responseMessage.contains(errorMessageResource.getString("ERROR_TOKEN_IS_BLANK")));
        }};
    }

    /**
     * Tests the mediator with a request with token headers.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testRequest() throws Exception {
        assertNotNull(system);
        new JavaTestKit(system) {{
            PowerMockito.mockStatic(MllpUtils.class);

            InputStream stream = ClientConflictsResolutionsOrchestratorTest.class.getClassLoader().getResourceAsStream("clients_conflicts_resolution.json");

            Assert.assertNotNull(stream);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("x-nhcr-token", "csv-sync-service");

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/nhcr-conflicts-resolution",
                    IOUtils.toString(stream),
                    headers,
                    Collections.emptyList()
            );

            orchestrator.tell(request, getRef());

            final Object[] out =
                    new ReceiveWhile<Object>(Object.class, duration("3 seconds")) {
                        @Override
                        protected Object match(Object msg) throws Exception {
                            if (msg instanceof FinishRequest) {
                                return msg;
                            }
                            throw noMatch();
                        }
                    }.get();
            int responseStatus = 0;

            for (Object o : out) {
                if (o instanceof FinishRequest) {
                    responseStatus = ((FinishRequest) o).getResponseStatus();
                    break;
                }
            }

            assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            assertEquals(200, responseStatus);
        }};
    }
}