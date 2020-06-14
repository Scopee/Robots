package gui.views;

import com.google.gson.JsonSerializer;
import gui.model.Robot;
import gui.serialization.DataWindowSerializer;
import gui.serialization.SavableInternalFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class DataWindow extends SavableInternalFrame implements Observer {
    private TextArea infoContent;

    public DataWindow() {
        super("Данные о роботе", true, true, true, true);
        setSize(300, 100);
        infoContent = new TextArea("");
        infoContent.setBackground(Color.WHITE);
        infoContent.setSize(300, 100);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(infoContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public JsonSerializer getSerializer() {
        return new DataWindowSerializer();
    }

    @Override
    public void update(Observable o, Object arg) {
        Robot robot = (Robot) arg;
        setInfo(robot.getX(), robot.getY(), robot.getDirection());
    }

    public void setRobot(Robot robot) {
        double x = robot.getX();
        double y = robot.getY();
        double direction = robot.getDirection();
        setInfo(x, y, direction);
    }

    private void setInfo(double x, double y, double direction) {
        String text = String.format("X: %.1f\nY: %.1f\nDirection: %.1f", x, y, direction);
        infoContent.setText(text);
        infoContent.invalidate();
    }
}
