package tz.go.moh.him.nhcr.mediator.orchestrator;

import org.apache.http.HttpStatus;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;

/**
 * Represents an NHCR orchestrator.
 */
public class NhcrOrchestrator extends BaseOrchestrator {

    /**
     * Initializes a new instance of the {@link DefaultOrchestrator} class.
     *
     * @param config The configuration.
     */
    public NhcrOrchestrator(MediatorConfig config) {
        super(config);
    }

    /**
     * Handles the received message.
     *
     * @param request The request.
     * @throws Exception if an exception occurs.
     */
    @Override
    protected void onReceiveRequestInternal(MediatorHTTPRequest request) throws Exception {
        request.getRequestHandler().tell(new FinishRequest("Success", "text/plain", HttpStatus.SC_OK), getSelf());
    }
}
