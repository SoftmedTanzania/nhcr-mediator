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

    /**
     * fingerPrint
     */
    @SerializedName("fingerPrint")
    @JsonProperty("fingerPrint")
    private FingerPrint fingerPrint;

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

    public FingerPrint getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(FingerPrint fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public class FingerPrint {
        /**
         * finger Print Image
         */
        @SerializedName("image")
        @JsonProperty("image")
        private String image;

        /**
         * finger Print Code e.g (R1, R2, R3, R4, R5, L1, L2, L3, L4, L5)
         */
        @SerializedName("code")
        @JsonProperty("code")
        private String code;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
