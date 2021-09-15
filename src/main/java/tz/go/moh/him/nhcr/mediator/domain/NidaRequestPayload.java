package tz.go.moh.him.nhcr.mediator.domain;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Payload")
public class NidaRequestPayload {
    @XmlElement(name = "NIN")
    private String nin;

    @XmlElement(name = "FINGERIMAGE")
    String fingerImage;

    @XmlElement(name = "FINGERCODE")
    String fingerCode;

    public void setNin(String nin) {
        this.nin = nin;
    }

    public void setFingerImage(String fingerImage) {
        this.fingerImage = fingerImage;
    }

    public void setFingerCode(String fingerCode) {
        this.fingerCode = fingerCode;
    }
}
