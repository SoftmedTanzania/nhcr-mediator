package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class EmrRequestForAssociatedConflictsOfMasterProfileMessage extends EmrMessage {
    /**
     * The CR CID of the master record with conflicts
     */
    @SerializedName("crCid")
    @JsonProperty("crCid")
    private String crCid;

    public String getCrCid() {
        return crCid;
    }

    public void setCrCid(String crCid) {
        this.crCid = crCid;
    }
}
