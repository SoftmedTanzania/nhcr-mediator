package tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import tz.go.moh.him.nhcr.mediator.domain.Client;

import java.lang.reflect.Type;

/**
 * Gson Custom Json Deserializer for {@link Client.PostOrUpdate} enums
 */
public class AttributePostOrUpdateDeserializer implements JsonDeserializer<Client.PostOrUpdate> {
    @Override
    public Client.PostOrUpdate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Client.PostOrUpdate[] postOrUpdates = Client.PostOrUpdate.values();
        for (Client.PostOrUpdate postOrUpdate : postOrUpdates) {
            if (postOrUpdate.getValue().equals(json.getAsString()))
                return postOrUpdate;
        }
        return null;
    }
}