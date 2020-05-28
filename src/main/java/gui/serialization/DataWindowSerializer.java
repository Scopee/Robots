package gui.serialization;

import com.google.gson.*;
import gui.views.DataWindow;

import java.lang.reflect.Type;

public class DataWindowSerializer extends DefaultSerializer implements JsonSerializer<DataWindow>, JsonDeserializer<DataWindow> {


    @Override
    public DataWindow deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DataWindow window = new DataWindow();
        JsonObject json = jsonElement.getAsJsonObject();
        super.deserialize(json, window);

        return window;
    }

    @Override
    public JsonElement serialize(DataWindow src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        super.serialize(src, json);

        return json;
    }
}
