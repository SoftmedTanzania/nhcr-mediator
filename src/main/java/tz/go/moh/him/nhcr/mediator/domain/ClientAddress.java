package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class ClientAddress {
    /**
     * Name of the Region
     */
    @SerializedName("region")
    @JsonProperty("region")
    private String region;

    /**
     * Name of the District Council
     */
    @SerializedName("council")
    @JsonProperty("council")
    private String council;

    /**
     * Name of the Ward
     */
    @SerializedName("ward")
    @JsonProperty("ward")
    private String ward;

    /**
     * Name of the Village
     */
    @SerializedName("village")
    @JsonProperty("village")
    private String village;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCouncil() {
        return council;
    }

    public void setCouncil(String council) {
        this.council = council;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}