package tz.go.moh.him.nhcr.mediator.orchestrator;

import akka.actor.UntypedActor;
import org.apache.http.HttpStatus;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;

/**
 * Represents a default orchestrator.
 */
public class DefaultOrchestrator extends UntypedActor {

    /**
     * Initializes a new instance of the {@link DefaultOrchestrator} class.
     *
     * @param config The configuration.
     */
    public DefaultOrchestrator(@SuppressWarnings("unused") MediatorConfig config) {
    }

    /**
     * Handles the received message.
     *
     * @param msg The received message.
     */
    @Override
    public void onReceive(Object msg) {
        if (msg instanceof MediatorHTTPRequest) {
            ((MediatorHTTPRequest) msg).getRequestHandler().tell(new FinishRequest("Success", "text/plain", HttpStatus.SC_OK), getSelf());
        } else {
            unhandled(msg);
        }
    }
}
