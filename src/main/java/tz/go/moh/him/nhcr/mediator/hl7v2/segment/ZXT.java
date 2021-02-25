package tz.go.moh.him.nhcr.mediator.hl7v2.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.v231.datatype.ST;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import tz.go.moh.him.nhcr.mediator.hl7v2.datatype.RitaID;

public class ZXT extends AbstractSegment {
    public ZXT(Group parent, ModelClassFactory factory) {
        super(parent, factory);
        // By convention, an init() method is created which adds
        // the specific fields to this segment class
        init(factory);
    }

    private void init(ModelClassFactory factory) {
        try {
            /*
             * For each field in the custom segment, the add() method is
             * called once. In this example, there are two fields in
             * the ZXT segment.
             *
             * See here for information on the arguments to this method:43
             * * http://hl7api.sourceforge.net/base/apidocs/ca/uhn/hl7v2/model/AbstractSegment.html#add%28java.lang.Class,%20boolean,%20int,%20int,%20java.lang.Object[],%20java.lang.String%29
             */
            add(ST.class, true, 0, 100, new Object[]{getMessage()}, "Voter's ID");
            add(RitaID.class, false, 1, 256, new Object[]{getMessage()}, "Rita ID");
        } catch (HL7Exception e) {
            log.error("Unexpected error creating ZXT - this is probably a bug in the source code generator.", e);
        }
    }

    /**
     * This method must be overridden. The easiest way is just to return null.
     */
    @Override
    protected Type createNewTypeWithoutReflection(int field) {
        return null;
    }

    /**
     * Create an accessor for each field
     */
    public ST getVotersId() throws HL7Exception {
        return (ST) this.getTypedField(1, 0);
    }

    /**
     * Create an accessor for each field
     */
    public RitaID getRitaId() throws HL7Exception {
        return (RitaID) this.getTypedField(2, 0);
    }
}
