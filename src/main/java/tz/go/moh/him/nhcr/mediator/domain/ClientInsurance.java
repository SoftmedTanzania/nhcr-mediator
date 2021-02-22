package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class ClientInsurance {
    /**
     * Insurance Id of the client
     */
    @SerializedName("id")
    @JsonProperty("id")
    private String id;

    /**
     * Name of the Insurance
     */
    @SerializedName("name")
    @JsonProperty("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

