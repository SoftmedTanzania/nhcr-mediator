package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Represents an EMR message.
 */
public class EmrMessage {

    /**
     * The sending facility.
     */
    @SerializedName("sendingFacility")
    @JsonProperty("sendingFacility")
    private String sendingFacility;

    /**
     * The facility HFR code.
     */
    @SerializedName("facilityHFRCode")
    @JsonProperty("facilityHFRCode")
    private String facilityHfrCode;

    /**
     * The sending Application.
     */
    @SerializedName("sendingApplication")
    @JsonProperty("sendingApplication")
    private String sendingApplication;

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
     * @param sendingApplication The sending application.
     */
    public EmrMessage(String sendingFacility, String facilityHfrCode, String sendingApplication) {
        this();
        this.setSendingFacility(sendingFacility);
        this.setFacilityHfrCode(facilityHfrCode);
        this.setSendingApplication(sendingApplication);
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

    /**
     * Gets the sending Application.
     *
     * @return Returns the sending Application.
     */
    public String getSendingApplication() {
        return sendingApplication;
    }

    /**
     * Sets the sending Application
     *
     * @param sendingApplication The sending Application
     */
    public void setSendingApplication(String sendingApplication) {
        this.sendingApplication = sendingApplication;
    }
}
