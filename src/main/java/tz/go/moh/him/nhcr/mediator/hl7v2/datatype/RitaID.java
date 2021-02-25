package tz.go.moh.him.nhcr.mediator.hl7v2.datatype;


import ca.uhn.hl7v2.model.AbstractComposite;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.v231.datatype.IS;
import ca.uhn.hl7v2.model.v231.datatype.ST;

public class RitaID extends AbstractComposite {
    private Type[] data;

    public RitaID(Message message) {
        super(message);
        this.init();
    }

    private void init() {
        this.data = new Type[5];
        this.data[0] = new ST(this.getMessage());
        this.data[1] = new IS(this.getMessage());
        this.data[2] = new IS(this.getMessage());
        this.data[3] = new ST(this.getMessage());
        this.data[4] = new ST(this.getMessage());
    }

    public Type[] getComponents() {
        return this.data;
    }

    public Type getComponent(int number) throws DataTypeException {
        try {
            return this.data[number];
        } catch (ArrayIndexOutOfBoundsException var3) {
            throw new DataTypeException("Element " + number + " doesn't exist (Type " + this.getClass().getName() + " has only " + this.data.length + " components)");
        }
    }

    public ST getId() {
        return (ST) this.getTyped(0, ST.class);
    }

    public IS getCountryCode() {
        return (IS) this.getTyped(1, IS.class);
    }

    public IS getIdType() {
        return (IS) this.getTyped(2, IS.class);
    }

    public ST getOther() {
        return (ST) this.getTyped(3, ST.class);
    }

    public ST getCountryName() {
        return (ST) this.getTyped(4, ST.class);
    }

}
