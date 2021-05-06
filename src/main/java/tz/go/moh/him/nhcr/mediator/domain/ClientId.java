package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class ClientId {
    /**
     * Id Value
     */
    @SerializedName("id")
    @JsonProperty("id")
    private String id;

    /**
     * Id Type
     */
    @SerializedName("type")
    @JsonProperty("type")
    private String type;

    /**
     * Initializes a new instance of the {@link ClientId} class.
     */
    public ClientId() {
    }

    /**
     * Initializes a new instance of the {@link ClientId} class.
     *
     * @param type The type.
     * @param id   The id value.
     */
    public ClientId(String type, String id) {
        this();

        this.setType(type);
        this.setId(id);
    }

    /**
     * Gets the id.
     *
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the type.
     *
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
}