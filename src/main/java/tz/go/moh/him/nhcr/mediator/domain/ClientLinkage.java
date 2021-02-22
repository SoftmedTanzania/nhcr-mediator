package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class ClientLinkage {
    /**
     * Id value of the Linked client
     */
    @SerializedName("id")
    @JsonProperty("id")
    private String id;

    /**
     * Issuing authority name of the Id
     */
    @SerializedName("sourceOfId")
    @JsonProperty("sourceOfId")
    private String sourceOfId;

    /**
     * Type of Linkage
     */
    @SerializedName("typeOfLinkage")
    @JsonProperty("typeOfLinkage")
    private String typeOfLinkage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceOfId() {
        return sourceOfId;
    }

    public void setSourceOfId(String sourceOfId) {
        this.sourceOfId = sourceOfId;
    }

    public String getTypeOfLinkage() {
        return typeOfLinkage;
    }

    public void setTypeOfLinkage(String typeOfLinkage) {
        this.typeOfLinkage = typeOfLinkage;
    }
}