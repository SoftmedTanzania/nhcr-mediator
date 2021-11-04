package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The associated conflicts of a master profile object returned to the EMR.
 */
public class Conflicts {
    /**
     * The associated conflicts returned.
     */
    @SerializedName("associatedConflicts")
    @JsonProperty("associatedConflicts")
    private List<Client> associatedConflicts;

    /**
     * The conflicting fields in the client's data.
     */
    @SerializedName("conflictingFields")
    @JsonProperty("conflictingFields")
    private List<String> conflictingFields;

    public List<Client> getAssociatedConflicts() {
        return associatedConflicts;
    }

    public void setAssociatedConflicts(List<Client> associatedConflicts) {
        this.associatedConflicts = associatedConflicts;
    }

    public List<String> getConflictingFields() {
        return conflictingFields;
    }

    public void setConflictingFields(List<String> conflictingFields) {
        this.conflictingFields = conflictingFields;
    }
}
