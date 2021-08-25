package tz.go.moh.him.nhcr.mediator.orchestrator;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a base orchestrator.
 */
public abstract class BaseOrchestrator extends UntypedActor {

    /**
     * The mediator config.
     */
    protected final MediatorConfig config;

    /**
     * Represents an Error Messages Definition Resource Object defined in <a href="file:../resources/error-messages.json">/resources/error-messages.json</a>.
     */
    protected JSONObject errorMessageResource;

    /**
     * The logger instance.
     */
    protected final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * Initializes a new instance of the {@link DefaultOrchestrator} class.
     *
     * @param config The configuration.
     */
    protected BaseOrchestrator(MediatorConfig config) {
        this.config = config;
        InputStream stream = getClass().getClassLoader().getResourceAsStream("error-messages.json");
        try {
            if (stream != null) {
                errorMessageResource = new JSONObject(IOUtils.toString(stream));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
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
