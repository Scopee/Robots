package gui.views;

import gui.model.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

public class GameVisualizer extends JPanel {
    private final Timer timer = initTimer();

    private static Timer initTimer() {
        Timer events_generator = new Timer("events generator", true);
        return events_generator;
    }

    private Robot robot;

    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;

    public GameVisualizer() {
        robot = new Robot(100, 100, 0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robot.onModelUpdateEvent(targetPositionX, targetPositionY, getWidth(), getHeight());
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(robot.getX()), round(robot.getY()), robot.getDirection());
        drawTarget(g2d, targetPositionX, targetPositionY);
    }

    protected void setTargetPosition(Point p) {
        targetPositionX = p.x;
        targetPositionY = p.y;
    }

    protected Point getTargetPosition() {
        return new Point(targetPositionX, targetPositionY);
    }

    protected void setRobot(double x, double y, double direction) {
        robot = new Robot(x, y, direction);
    }

    protected Robot getRobot() {
        return robot;
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }


    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(robot.getX());
        int robotCenterY = round(robot.getY());
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }


}
