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
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains tests for the {@link NidaActor} class.
 */
@RunWith(PowerMockRunner.class)
public class NidaClientsSearchOrchestratorTest extends BaseOrchestratorTest {

    /**
     * The orchestrator.
     */
    private final ActorRef orchestrator = system.actorOf(Props.create(NidaActor.class, configuration));

    /**
     * Tests the mediator with a request with token headers.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testRequest() throws Exception {
        Assert.assertNotNull(system);
        new JavaTestKit(system) {{
            InputStream stream = NidaClientsSearchOrchestratorTest.class.getClassLoader().getResourceAsStream("nida_clients_search_request.json");

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("x-nhcr-token", "34424242");

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/nhcr-clients-search",
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
            String response = null;
            
            for (Object o : out) {
                if (o instanceof FinishRequest) {
                    responseStatus = ((FinishRequest) o).getResponseStatus();
                    response = ((FinishRequest) o).getResponse();
                    break;
                }
            }

            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            Assert.assertEquals(200, responseStatus);

            InputStream successfulResponseStream = NidaClientsSearchOrchestratorTest.class.getClassLoader().getResourceAsStream("successful_client_search_results.json");
            Assert.assertNotNull(successfulResponseStream);
            Assert.assertEquals((IOUtils.toString(successfulResponseStream)),response);
        }};
    }
}