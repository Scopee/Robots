package gui;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gui.serialization.Savable;
import gui.views.DataWindow;
import gui.views.GameWindow;
import gui.views.MainApplicationFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RobotsProgram {
    static MainApplicationFrame frame;
    public static final String FILENAME = "save.json";

    public static void main(String[] args) {
        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            load();
            frame.setVisible(true);
        });
    }

    public static void save(MainApplicationFrame mainFrame) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("mainApplicationFrame", new JsonParser().parse(new GsonBuilder()
                .registerTypeAdapter(mainFrame.getClass(), MainApplicationFrame.getSerializer())
                .setPrettyPrinting()
                .create()
                .toJson(mainFrame)));
        for (String name : frame.frames.keySet()) {
            jsonObject.add(name, new JsonParser().parse(new GsonBuilder()
                    .registerTypeAdapter(frame.frames.get(name).getClass(), frame.frames.get(name).getSerializer())
                    .setPrettyPrinting()
                    .create()
                    .toJson(frame.frames.get(name))));
        }

        try (FileWriter writer = new FileWriter(FILENAME, false)) {
            writer.write(jsonObject.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            frame = new MainApplicationFrame();
            return;
        }
        try (Scanner in = new Scanner(file)) {
            String jsonString = in.nextLine();
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();

            frame = new GsonBuilder()
                    .registerTypeAdapter(MainApplicationFrame.class, MainApplicationFrame.getSerializer())
                    .create()
                    .fromJson(json.get("mainApplicationFrame").getAsJsonObject(), MainApplicationFrame.class);

            for (String name : frame.frames.keySet()) {
                JInternalFrame internalFrame = frame.frames.get(name);
                Savable s = new GsonBuilder()
                        .registerTypeAdapter(frame.frames.get(name).getClass(), frame.frames.get(name).getSerializer())
                        .create()
                        .fromJson(json.get(name).getAsJsonObject(), frame.frames.get(name).getClass());
                frame.desktopPane.remove(internalFrame);
                frame.addWindow((JInternalFrame) s, name);
            }
            GameWindow gw = (GameWindow) frame.frames.get("gameWindow");
            DataWindow dw = (DataWindow) frame.frames.get("dataWindow");
            gw.getRobot().addObserver(dw);
            dw.setRobot(gw.getRobot());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
