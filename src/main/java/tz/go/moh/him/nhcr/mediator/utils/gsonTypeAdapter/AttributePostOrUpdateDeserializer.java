package tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import tz.go.moh.him.nhcr.mediator.domain.EmrClientsRegistrationAndUpdatesMessage;

import java.lang.reflect.Type;

/**
 * Gson Custom Json Deserializer for {@link EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate} enums
 */
public class AttributePostOrUpdateDeserializer implements JsonDeserializer<EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate> {
    @Override
    public EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate[] postOrUpdates = EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate.values();
        for (EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate postOrUpdate : postOrUpdates) {
            if (postOrUpdate.getValue().equals(json.getAsString()))
                return postOrUpdate;
        }
        return null;
    }
}