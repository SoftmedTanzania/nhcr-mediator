package tz.go.moh.him.nhcr.mediator.hl7v2.v231.group;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.v231.group.ADR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2;
import ca.uhn.hl7v2.model.v231.segment.IN1;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.segment.ZXT;

import java.util.Arrays;

public class ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT extends ADR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2 {

    public ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT(Group parent, ModelClassFactory factory) {
        super(parent, factory);
        // By convention, an init() method is created which adds
        // the specific fields to this segment class
        init(factory);
    }

    /**
     * Initializes this instance.
     *
     * @param factory The factory.
     */
    private void init(ModelClassFactory factory) {
        try {
            // Now, let's add the ZXT segment at the right spot
            String[] segmentNames = getNames();
            int indexOfUB2 = Arrays.asList(segmentNames).indexOf("PID");

            // Put the ZXT segment right after the PID segment
            int index = indexOfUB2 + 1;

            // in1
            Class<IN1> in1 = IN1.class;
            boolean required = false;
            boolean repeating = false;

            this.add(in1, required, repeating, index);

            // zxt
            Class<ZXT> zxt = ZXT.class;

            this.add(zxt, required, repeating, index + 2);
        } catch (HL7Exception e) {
            log.error("Unexpected error creating ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT group - this is probably a bug in the source code generator.", e);
        }
    }

    /**
     * Add an accessor for the IN1 segment
     *
     * @return Returns the IN1 segment.
     */
    public IN1 getIN1() {
        return getTyped("IN1", IN1.class);
    }

    /**
     * Add an accessor for the ZXT segment
     *
     * @return Returns the ZXT segment.
     */
    public ZXT getZXT() {
        return getTyped("ZXT", ZXT.class);
    }
}
