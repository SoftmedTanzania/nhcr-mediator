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
     * Name of the Program matching the id
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