package tz.go.moh.him.hfr.mediator.orchestrator;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * Contains tests for the {@link DefaultOrchestrator} class.
 */
public class DefaultOrchestratorTest extends BaseOrchestratorTest {

    private final ActorRef orchestrator = system.actorOf(Props.create(DefaultOrchestrator.class, configuration));

    /**
     * Tests the mediator.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testHfrRequest() throws Exception {
        new JavaTestKit(system) {{

            InputStream stream = DefaultOrchestratorTest.class.getClassLoader().getResourceAsStream("request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/hfr-inbound",
                    IOUtils.toString(stream),
                    Collections.singletonMap("Content-Type", "application/json"),
                    Collections.emptyList()
            );

            orchestrator.tell(request, getRef());

            final Object[] out = new ReceiveWhile<Object>(Object.class, duration("3 seconds")) {
                @Override
                protected Object match(Object msg) {
                    if (msg instanceof FinishRequest) {
                        return msg;
                    }
                    throw noMatch();
                }
            }.get();

            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
        }};
    }

    /**
     * Tests the mediator.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testHfrRequestBadRequest() throws Exception {
        new JavaTestKit(system) {{

            InputStream stream = DefaultOrchestratorTest.class.getClassLoader().getResourceAsStream("bad_request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/hfr-inbound",
                    IOUtils.toString(stream),
                    Collections.singletonMap("Content-Type", "application/json"),
                    Collections.emptyList()
            );

            orchestrator.tell(request, getRef());

            final Object[] out = new ReceiveWhile<Object>(Object.class, duration("3 seconds")) {
                @Override
                protected Object match(Object msg) {
                    if (msg instanceof FinishRequest) {
                        return msg;
                    }
                    throw noMatch();
                }
            }.get();

            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            Assert.assertTrue(Arrays.stream(out).allMatch(c -> (c instanceof FinishRequest) && ((FinishRequest) c).getResponse().equals("Bad Request")));
        }};
    }
}