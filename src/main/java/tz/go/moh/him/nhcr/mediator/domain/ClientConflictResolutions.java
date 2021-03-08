package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientConflictResolutions extends Client {
    /**
     * The list of NHCR assigned identifiers of all the conflicts (excluding the survivor)
     */
    @SerializedName("mergedRecords")
    @JsonProperty("mergedRecords")
    List<CrId> mergedRecords;

    public List<CrId> getMergedRecords() {
        return mergedRecords;
    }

    public void setMergedRecords(List<CrId> mergedRecords) {
        this.mergedRecords = mergedRecords;
    }

    public static class CrId {
        /**
         * NHCR assigned identifier of conflict (excluding the survivor)
         */
        private String crId;

        public String getCrId() {
            return crId;
        }

        public void setCrId(String crId) {
            this.crId = crId;
        }
    }
}
