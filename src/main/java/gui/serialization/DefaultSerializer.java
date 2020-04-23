package gui.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import javax.swing.*;
import java.beans.PropertyVetoException;

public class DefaultSerializer {
    public JInternalFrame deserialize(JsonObject json, JInternalFrame frame) throws JsonParseException {
        frame.setLocation(json.get("x").getAsInt(), json.get("y").getAsInt());
        frame.setSize(json.get("width").getAsInt(), json.get("height").getAsInt());

        try {
            frame.setIcon(json.get("isIcon").getAsBoolean());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        return frame;
    }

    public JsonElement serialize(JInternalFrame src, JsonObject json) {
        json.addProperty("x", src.getX());
        json.addProperty("y", src.getY());
        json.addProperty("width", src.getWidth());
        json.addProperty("height", src.getHeight());
        json.addProperty("isIcon", src.isIcon());

        return json;
    }
}
