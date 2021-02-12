package tz.go.moh.him.nhcr.mediator.utils;

import ca.uhn.hl7v2.model.v231.message.ADT_A04;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contains tests for the {@link HL7v2MessageBuilderUtils} class.
 */
public class HL7v2MessageBuilderTests {

    /**
     * Tests the message builder.
     */
    @Test
    public void testCreateAdtA04() {
        ADT_A04 actual = HL7v2MessageBuilderUtils.createAdtA04();

        Assert.assertEquals(ADT_A04.class, actual.getClass());
    }
}
