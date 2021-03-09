package tz.go.moh.him.nhcr.mediator.domain;

import org.junit.Assert;
import org.junit.Test;
import tz.go.moh.him.mediator.core.serialization.JsonSerializer;

import java.util.Arrays;

/**
 * Contains tests for the {@link EmrResponse} class.
 */
public class EmrResponseTest {
    /**
     * Tests the deserialization of an EMR Response message.
     */
    @Test
    public void testEmrResponseDeserialization() {
        JsonSerializer serializer = new JsonSerializer();

        EmrResponse.Summary summary = new EmrResponse.Summary();
        summary.setFailed(0);
        summary.setSuccessful(10);
        summary.setTotalClients(10);

        EmrResponse.FailedClientsMrn failedClientsMrn = new EmrResponse.FailedClientsMrn();
        failedClientsMrn.setMrn("mrn:334443434");
        failedClientsMrn.setError("error");

        EmrResponse emrResponse = new EmrResponse();
        emrResponse.setSummary(summary);
        emrResponse.setFailedClientsMrns(Arrays.asList(failedClientsMrn));

        String actual = serializer.serializeToString(emrResponse);


        Assert.assertTrue(actual.contains(String.valueOf(summary.getTotalClients())));
        Assert.assertTrue(actual.contains(String.valueOf(summary.getSuccessful())));
        Assert.assertTrue(actual.contains(String.valueOf(summary.getFailed())));

        Assert.assertTrue(actual.contains(failedClientsMrn.getMrn()));
        Assert.assertTrue(actual.contains(failedClientsMrn.getError()));
    }

}