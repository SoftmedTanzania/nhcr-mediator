package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class ClientProgram {
    /**
     * Id of the client within the Program
     */
    @SerializedName("id")
    @JsonProperty("id")
    private String id;

    /**
     * Name of the Program matching the assigning authority
     */
    @SerializedName("assigningAuthority")
    @JsonProperty("assigningAuthority")
    private String assigningAuthority;

    /**
     * HFR Code of the assigning facility
     */
    @SerializedName("assigningFacility")
    @JsonProperty("assigningFacility")
    private String assigningFacility;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssigningAuthority() {
        return assigningAuthority;
    }

    public void setAssigningAuthority(String assigningAuthority) {
        this.assigningAuthority = assigningAuthority;
    }

    public String getAssigningFacility() {
        return assigningFacility;
    }

    public void setAssigningFacility(String assigningFacility) {
        this.assigningFacility = assigningFacility;
    }
}