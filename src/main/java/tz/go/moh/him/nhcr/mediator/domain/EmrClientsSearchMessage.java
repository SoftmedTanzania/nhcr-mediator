package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class EmrClientsSearchMessage extends EmrMessage {
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
     * Limit
     */
    @SerializedName("limit")
    @JsonProperty("limit")
    private String limit;

    /**
     * Offset
     */
    @SerializedName("offset")
    @JsonProperty("offset")
    private String offset;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
