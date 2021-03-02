package tz.go.moh.him.nhcr.mediator.orchestrator;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.model.Message;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import tz.go.moh.him.nhcr.mediator.utils.HL7v2MessageBuilderUtils;
import tz.go.moh.him.nhcr.mediator.utils.MllpUtils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

/**
 * Contains tests for the {@link DefaultOrchestrator} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HL7v2MessageBuilderUtils.class, MllpUtils.class})
public class ClientRegistrationAndUpdatesOrchestratorTest extends BaseOrchestratorTest {

    /**
     * The orchestrator.
     */
    private final ActorRef orchestrator = system.actorOf(Props.create(ClientRegistrationAndUpdatesOrchestrator.class, configuration));

    /**
     * Tests the mediator.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testRequest() throws Exception {
        new JavaTestKit(system) {{
            PowerMockito.mockStatic(MllpUtils.class);
            Mockito.when(MllpUtils.sendMessage(any(Message.class), any(MediatorConfig.class), any(HapiContext.class), any(Connection.class))).thenReturn(null);

            InputStream stream = ClientRegistrationAndUpdatesOrchestratorTest.class.getClassLoader().getResourceAsStream("register_client.json");

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
}