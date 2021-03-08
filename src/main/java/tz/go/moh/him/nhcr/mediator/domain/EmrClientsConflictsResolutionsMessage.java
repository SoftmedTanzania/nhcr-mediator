package tz.go.moh.him.nhcr.mediator.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents an EMR Emr Clients Conflicts Resolutions message.
 */
public class EmrClientsConflictsResolutionsMessage extends EmrMessage {
    /**
     * The resolved clients conflicts list.
     */
    @SerializedName("clients")
    @JsonProperty("clients")
    private List<ClientConflictResolutions> clients;

    /**
     * Gets the resolved clients conflicts list.
     *
     * @return the resolved clients conflicts list.
     */
    public List<ClientConflictResolutions> getClients() {
        return clients;
    }

    /**
     * Sets the resolved clients conflicts list.
     *
     * @param clients Sets the resolved clients conflicts list.
     */
    public void setClients(List<ClientConflictResolutions> clients) {
        this.clients = clients;
    }

}
