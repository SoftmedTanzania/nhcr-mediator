package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class PhoneNumber {
    /**
     * Phone number prefix
     */
    @SerializedName("prefix")
    @JsonProperty("prefix")
    private String prefix;

    /**
     * Phone number suffix
     */
    @SerializedName("number")
    @JsonProperty("number")
    private String number;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
