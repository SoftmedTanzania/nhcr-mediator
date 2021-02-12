package tz.go.moh.him.nhcr.mediator.utils;

import ca.uhn.hl7v2.model.v231.message.ADT_A04;

/**
 * Represents an HL7v2 message builder utility.
 */
public class HL7v2MessageBuilderUtils {

    /**
     * Creates an ADT A04 message.
     *
     * @return Returns the created ADT A04 message.
     */
    public static ADT_A04 createAdtA04() {
        return new ADT_A04();
    }
}
