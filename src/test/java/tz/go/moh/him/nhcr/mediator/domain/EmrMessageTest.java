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

        EmrMessage message = new EmrMessage("Mirembe MHH", "Blaj9747","AFYA CARE","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvbmhjci5yYXN4cC5jb206ODA4MFwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYxMjkzMTk3NiwiZXhwIjoxNjEyOTM1NTc2LCJuYmYiOjE2MTI5MzE5NzYsImp0aSI6IlVDb21HNVpQVlN1Wk9KMFgiLCJzdWIiOjEsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.UzmHbKcgrgIFdxRGfs74Oyb1C1lvgigk5IDcCvePLis");

        String actual = serializer.serializeToString(message);

        Assert.assertTrue(actual.contains(message.getFacilityHfrCode()));
        Assert.assertTrue(actual.contains(message.getSendingFacility()));
        Assert.assertTrue(actual.contains(message.getOid()));
        Assert.assertTrue(actual.contains(message.getToken()));
    }
}
