package tz.go.moh.him.nhcr.mediator.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Represents an EMR message.
 */
public class EmrMessage {

    /**
     * The sending facility.
     */
    @SerializedName("SendingFacility")
    private String sendingFacility;

    /**
     * The facility HFR code.
     */
    @SerializedName("FacilityHFRCode")
    private String facilityHfrCode;

    /**
     * The sending facility OID.
     */
    @SerializedName("oid")
    private String oid;

    /**
     * The sending facility security token.
     */
    @SerializedName("token")
    private String token;

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
    public EmrMessage(String sendingFacility, String facilityHfrCode, String oid, String token) {
        this();
        this.setSendingFacility(sendingFacility);
        this.setFacilityHfrCode(facilityHfrCode);
        this.setOid(oid);
        this.setToken(token);
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
     * Gets the facility NHCR OID.
     *
     * @return Returns the facility NHCR OID.
     */
    public String getOid() {
        return oid;
    }

    /**
     * Sets the facility NHCR OID.
     *
     * @param oid The facility NHCR oid.
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Gets the facility NHCR security token.
     *
     * @return Returns the facility NHCR security token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the facility NHCR security token.
     *
     * @param token The facility NHCR security token.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
