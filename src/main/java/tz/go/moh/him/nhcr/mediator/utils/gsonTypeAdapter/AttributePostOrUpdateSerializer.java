package tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tz.go.moh.him.nhcr.mediator.domain.EmrClientsRegistrationAndUpdatesMessage;

import java.lang.reflect.Type;

/**
 * Gson Custom Json Serializer for {@link EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate} enums
 */
public class AttributePostOrUpdateSerializer implements JsonSerializer<EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate> {
    @Override
    public JsonElement serialize(EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate postOrUpdate, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(postOrUpdate.getValue());
    }
}