package tz.go.moh.him.nhcr.mediator.hl7v2.v231.message;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v231.message.ADT_A01;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.segment.ZXT;

import java.util.Arrays;

@SuppressWarnings("serial")
public class ZXT_A01 extends ADT_A01 {
    /**
     * Constructor
     */
    public ZXT_A01() throws HL7Exception {
        this(new DefaultModelClassFactory());
    }

    /**
     * Constructor
     * <p>
     * We always have to have a constructor with this one argument
     */
    public ZXT_A01(ModelClassFactory factory) throws HL7Exception {
        super(factory);

        // Now, let's add the ZXT segment at the right spot
        String[] segmentNames = getNames();
        int indexOfIn1 = Arrays.asList(segmentNames).indexOf("IN1IN2IN3");

        // Put the ZXT segment right after the IN1 segment
        int index = indexOfIn1 + 1;

        Class<ZXT> type = ZXT.class;
        boolean required = false;
        boolean repeating = false;

        this.add(type, required, repeating, index);
    }

    /**
     * Add an accessor for the ZXT segment
     */
    public ZXT getZXT() throws HL7Exception {
        return (ZXT) get("ZXT");
    }

}
