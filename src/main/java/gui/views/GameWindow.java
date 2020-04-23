package gui.views;

import com.google.gson.JsonSerializer;
import gui.model.Robot;
import gui.serialization.GameWindowSerializer;
import gui.serialization.SavableInternalFrame;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends SavableInternalFrame {
    private final GameVisualizer visualizer;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public Robot getRobot() {
        return visualizer.getRobot();
    }

    public void setRobot(double x, double y, double direction) {
        visualizer.setRobot(x, y, direction);
    }

    public Point getTargetPosition() {
        return visualizer.getTargetPosition();
    }

    public void setTarget(int x, int y) {
        visualizer.setTargetPosition(new Point(x, y));
    }

    @Override
    public JsonSerializer getSerializer() {
        return new GameWindowSerializer();
    }
}
