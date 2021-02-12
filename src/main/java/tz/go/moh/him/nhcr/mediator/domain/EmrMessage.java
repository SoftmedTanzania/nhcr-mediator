package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an EMR message.
 */
public class EmrMessage {

    /**
     * The sending facility.
     */
    @JsonProperty("SendingFacility")
    private String sendingFacility;

    /**
     * The facility HFR code.
     */
    @JsonProperty("FacilityHFRCode")
    private String facilityHfrCode;

    /**
     * Initializes a new instance of the {@link EmrMessage} class.
     */
    public EmrMessage() {
    }

    /**
     * Initializes a new instance of the {@link EmrMessage} class.
     *
     * @param sendingFacility The sending facility.
     * @param facilityHfrCode The facility HFR code.
     */
    public EmrMessage(String sendingFacility, String facilityHfrCode) {
        this();
        this.setSendingFacility(sendingFacility);
        this.setFacilityHfrCode(facilityHfrCode);
    }

    /**
     * Gets the sending facility.
     *
     * @return Returns the sending facliity.
     */
    public String getSendingFacility() {
        return sendingFacility;
    }

    /**
     * Sets the sending facility.
     *
     * @param sendingFacility The sending facility.
     */
    public void setSendingFacility(String sendingFacility) {
        this.sendingFacility = sendingFacility;
    }

    /**
     * Gets the facility HFR code.
     *
     * @return Returns the facility HFR code.
     */
    public String getFacilityHfrCode() {
        return facilityHfrCode;
    }

    /**
     * Sets the facility HFR code.
     *
     * @param facilityHfrCode The facility HFR code.
     */
    public void setFacilityHfrCode(String facilityHfrCode) {
        this.facilityHfrCode = facilityHfrCode;
    }
}
