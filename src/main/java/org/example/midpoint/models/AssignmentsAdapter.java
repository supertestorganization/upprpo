package org.example.midpoint.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AssignmentsAdapter implements JsonDeserializer<List<Assignment>> {
    @Override
    public List<Assignment> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<Assignment> assignments = new ArrayList<>();
        if (jsonElement.isJsonArray()) {
            for (JsonElement e : jsonElement.getAsJsonArray()) {
                assignments.add((Assignment) jsonDeserializationContext.deserialize(e, Assignment.class));
            }
        }else if (jsonElement.isJsonObject()) {
            assignments.add((Assignment) jsonDeserializationContext.deserialize(jsonElement, Assignment.class));
        }
        return assignments;
    }
}
