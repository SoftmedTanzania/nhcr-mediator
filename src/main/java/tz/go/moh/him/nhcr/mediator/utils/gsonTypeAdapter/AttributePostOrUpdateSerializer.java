package tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tz.go.moh.him.nhcr.mediator.domain.Client;

import java.lang.reflect.Type;

/**
 * Gson Custom Json Serializer for {@link Client.PostOrUpdate} enums
 */
public class AttributePostOrUpdateSerializer implements JsonSerializer<Client.PostOrUpdate> {
    @Override
    public JsonElement serialize(Client.PostOrUpdate postOrUpdate, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(postOrUpdate.getValue());
    }
}