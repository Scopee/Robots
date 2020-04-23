package gui.serialization;

import com.google.gson.*;
import gui.model.Robot;
import gui.views.GameWindow;

import java.lang.reflect.Type;

public class GameWindowSerializer extends DefaultSerializer implements JsonDeserializer<GameWindow>, JsonSerializer<GameWindow> {
    @Override
    public GameWindow deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        GameWindow window = new GameWindow();
        JsonObject json = jsonElement.getAsJsonObject();
        super.deserialize(json, window);

        JsonObject robot = json.get("robot").getAsJsonObject();
        window.setRobot(robot.get("robotX").getAsDouble(), robot.get("robotY").getAsDouble(), robot.get("direction").getAsDouble());
        window.setTarget(json.get("targetX").getAsInt(), json.get("targetY").getAsInt());

        return window;
    }

    @Override
    public JsonElement serialize(GameWindow src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        super.serialize(src, json);

        json.addProperty("targetX", src.getTargetPosition().getX());
        json.addProperty("targetY", src.getTargetPosition().getY());

        JsonObject robot = new JsonObject();
        Robot r = src.getRobot();
        robot.addProperty("robotX", r.getX());
        robot.addProperty("robotY", r.getY());
        robot.addProperty("direction", r.getDirection());
        json.add("robot", robot);
        return json;
    }
}
