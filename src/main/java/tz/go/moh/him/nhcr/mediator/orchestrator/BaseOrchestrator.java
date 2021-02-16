package tz.go.moh.him.nhcr.mediator.orchestrator;

import akka.actor.UntypedActor;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;

/**
 * Represents a base orchestrator.
 */
public abstract class BaseOrchestrator extends UntypedActor {

    /**
     * The mediator config.
     */
    protected final MediatorConfig config;

    /**
     * Initializes a new instance of the {@link DefaultOrchestrator} class.
     *
     * @param config The configuration.
     */
    protected BaseOrchestrator(MediatorConfig config) {
        this.config = config;
    }

    /**
     * Handles the received message.
     *
     * @param message The received message.
     * @throws Exception if an exception occurs.
     */
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MediatorHTTPRequest) {
            this.onReceiveRequestInternal((MediatorHTTPRequest) message);
        } else {
            unhandled(message);
        }
    }

    /**
     * Handles the received message.
     *
     * @param request The request.
     * @throws Exception if an exception occurs.
     */
    protected abstract void onReceiveRequestInternal(MediatorHTTPRequest request) throws Exception;

}
