package tz.go.moh.him.nhcr.mediator.domain;

import org.junit.Assert;
import org.junit.Test;
import tz.go.moh.him.mediator.core.serialization.JsonSerializer;

/**
 * Contains tests for the {@link EmrMessage} class.
 */
public class EmrMessageTest {

    /**
     * Tests the serialization of an EMR message.
     */
    @Test
    public void testEmrMessageSerialization() {
        JsonSerializer serializer = new JsonSerializer();

        EmrMessage message = new EmrMessage("Mirembe MHH", "Blaj9747");

        String actual = serializer.serializeToString(message);

        Assert.assertTrue(actual.contains(message.getFacilityHfrCode()));
        Assert.assertTrue(actual.contains(message.getSendingFacility()));
    }
}
