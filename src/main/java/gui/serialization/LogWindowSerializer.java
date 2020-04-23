package gui.serialization;

import com.google.gson.*;
import gui.views.LogWindow;
import log.LogEntry;
import log.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LogWindowSerializer extends DefaultSerializer implements JsonDeserializer<LogWindow>, JsonSerializer<LogWindow> {
    public LogWindow deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LogWindow window = new LogWindow(Logger.getDefaultLogSource());
        JsonArray array = jsonElement.getAsJsonObject().get("messages").getAsJsonArray();
        super.deserialize(jsonElement.getAsJsonObject(), window);
        return window;
    }

    public JsonElement serialize(LogWindow src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        super.serialize(src, json);

        ArrayList<LogEntry> messages = src.getMessages();
        JsonArray array = new JsonArray();
        for (LogEntry message : messages) {
            array.add(message.getMessage());
        }

        json.add("messages", array);
        return json;
    }
}
