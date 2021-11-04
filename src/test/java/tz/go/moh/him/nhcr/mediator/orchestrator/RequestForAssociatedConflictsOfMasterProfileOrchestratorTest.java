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
import tz.go.moh.him.mediator.core.serialization.JsonSerializer;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.utils.MllpUtils;

import java.io.InputStream;
import java.util.*;

/**
 * Contains tests for the {@link RequestForAssociatedConflictsOfMasterProfileOrchestrator} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MllpUtils.class)
public class RequestForAssociatedConflictsOfMasterProfileOrchestratorTest extends BaseOrchestratorTest {

    /**
     * The orchestrator.
     */
    private final ActorRef orchestrator = system.actorOf(Props.create(RequestForAssociatedConflictsOfMasterProfileOrchestrator.class, configuration));

    /**
     * Tests the mediator with a request with missing token headers.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testWithMissingTokenRequest() throws Exception {
        Assert.assertNotNull(system);
        new JavaTestKit(system) {{
            PowerMockito.mockStatic(MllpUtils.class);

            InputStream stream = RequestForAssociatedConflictsOfMasterProfileOrchestratorTest.class.getClassLoader().getResourceAsStream("request_for_conflicts_of_a_master_profile_request.json");

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/nhcr-request-for-associated-conflicts-of-master-profile",
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

            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            Assert.assertEquals(400, responseStatus);
            Assert.assertTrue(responseMessage.contains(errorMessageResource.getString("ERROR_TOKEN_IS_BLANK")));
        }};
    }

    /**
     * Tests the mediator with a request with token headers.
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testRequest() throws Exception {
        Assert.assertNotNull(system);
        new JavaTestKit(system) {{
            PowerMockito.mockStatic(MllpUtils.class);

            InputStream stream = RequestForAssociatedConflictsOfMasterProfileOrchestratorTest.class.getClassLoader().getResourceAsStream("request_for_conflicts_of_a_master_profile_request.json");

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
                    "/nhcr-request-for-associated-conflicts-of-master-profile",
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

            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            Assert.assertEquals(500, responseStatus);
        }};
    }

    /**
     * Tests getting mismatching field names
     *
     * @throws Exception if an exception occurs
     */
    @Test
    public void testGettingDifferenceBetweenTwoObjects() throws Exception {
        InputStream stream = RequestForAssociatedConflictsOfMasterProfileOrchestratorTest.class.getClassLoader().getResourceAsStream("associated_conflicts.json");
        List<Client> associatedConflicts = Arrays.asList(new JsonSerializer().deserialize(IOUtils.toString(stream), Client[].class));

        List<String> conflictingFields = RequestForAssociatedConflictsOfMasterProfileOrchestrator.difference(associatedConflicts);

        Assert.assertTrue(conflictingFields.contains("CTC"));
        Assert.assertTrue(conflictingFields.contains("CR_CID"));
        Assert.assertTrue(conflictingFields.contains("placeOfBirth"));
        Assert.assertTrue(conflictingFields.contains("firstName"));
        Assert.assertTrue(conflictingFields.contains("middleName"));
        Assert.assertTrue(conflictingFields.contains("lastName"));
        Assert.assertTrue(conflictingFields.contains("ids"));
        Assert.assertTrue(conflictingFields.contains("deathDate"));
        Assert.assertTrue(conflictingFields.size() == 14);
    }
}