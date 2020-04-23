package gui.serialization;

import com.google.gson.*;
import gui.views.MainApplicationFrame;

import java.lang.reflect.Type;

public class MainFrameSerializer implements JsonSerializer<MainApplicationFrame>, JsonDeserializer<MainApplicationFrame> {

    @Override
    public MainApplicationFrame deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MainApplicationFrame frame = new MainApplicationFrame();
        JsonObject json = jsonElement.getAsJsonObject();
        frame.setLocation(json.get("x").getAsInt(), json.get("y").getAsInt());
        frame.setSize(json.get("width").getAsInt(), json.get("height").getAsInt());

        return frame;
    }

    @Override
    public JsonElement serialize(MainApplicationFrame src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("x", src.getX());
        json.addProperty("y", src.getY());
        json.addProperty("width", src.getWidth());
        json.addProperty("height", src.getHeight());
        return json;
    }
}
