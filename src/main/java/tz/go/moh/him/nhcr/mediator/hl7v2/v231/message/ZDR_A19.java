package tz.go.moh.him.nhcr.mediator.hl7v2.v231.message;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.v231.segment.ERR;
import ca.uhn.hl7v2.model.v231.segment.MSA;
import ca.uhn.hl7v2.model.v231.segment.MSH;
import ca.uhn.hl7v2.model.v231.segment.QAK;
import ca.uhn.hl7v2.model.v231.segment.QRD;
import ca.uhn.hl7v2.model.v231.segment.QRF;
import ca.uhn.hl7v2.model.v231.segment.DSC;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.group.ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT;

/**
 * Represents an HL7v2 message which is a derivative of ADR_A19, representing a query response.
 */
public class ZDR_A19 extends AbstractMessage {
    /**
     * The constructor
     *
     * @throws HL7Exception The thrown exception
     */
    public ZDR_A19() throws HL7Exception {
        this(new DefaultModelClassFactory());
    }

    /**
     * Constructor
     * <p>
     * We always have to have a constructor with this one argument
     *
     * @param factory The Model factory used
     * @throws HL7Exception The exception thrown
     */
    public ZDR_A19(ModelClassFactory factory) throws HL7Exception {
        super(factory);

        try {
            this.add(MSH.class, true, false);
            this.add(MSA.class, true, false);
            this.add(ERR.class, false, false);
            this.add(QAK.class, false, false);
            this.add(QRD.class, true, false);
            this.add(QRF.class, false, false);
            this.add(ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT.class, true, true);
            this.add(DSC.class, false, false);
        } catch(HL7Exception e) {
            log.error("Unexpected error creating ADR_A19 - this is probably a bug in the source code generator.", e);
        }
    }

    /**
     * Add an accessor for number of EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT groups
     *
     * @return Returns number of EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT groups.
     */
    public int getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXTReps() {
        return getReps("EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT");
    }

    /**
     * Add an accessor for all EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT segments
     *
     * @return The EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT segments
     * @throws HL7Exception The exception thrown
     */
    public java.util.List<ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT> getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXTAll() throws HL7Exception {
        return getAllAsList("EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT", ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT.class);
    }
}
