package tz.go.moh.him.nhcr.mediator.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents an EMR EmrClientsRegistrationAndUpdates message.
 */
public class EmrClientsRegistrationAndUpdatesMessage extends EmrMessage {
    /**
     * The registered or updated clients list.
     */
    @SerializedName("clients")
    @JsonProperty("clients")
    private List<Client> clients;

    /**
     * Gets the registered or updated clients list.
     *
     * @return the registered or updated clients list.
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Sets the registered or updated clients list.
     *
     * @param clients Sets the registered or updated clients list.
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

}
