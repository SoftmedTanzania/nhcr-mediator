package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class EmrRequestForConflictsMessage extends EmrMessage {
    /**
     * Start Timestamp
     */
    @SerializedName("timestamp_start")
    @JsonProperty("timestamp_start")
    private String startDateTime;

    /**
     * End Timestamp
     */
    @SerializedName("timestamp_end")
    @JsonProperty("timestamp_end")
    private String endDateTime;

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

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
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
