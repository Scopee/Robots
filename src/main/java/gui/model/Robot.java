package gui.model;

import java.util.Observable;
import java.util.Observer;

public class Robot extends Observable {
    private volatile double x;
    private volatile double y;
    private volatile double direction;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    public Robot(double x, double y, double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public void registerObserver(Observer o){
        addObserver(o);
    }

    public void onModelUpdateEvent(int targetX, int targetY, int width, int height) {
        double distance = distance(targetX, targetY, x, y);
        if (distance < 0.5) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(x, y, targetX, targetY);
        double angularVelocity = 0;
        if (angleToTarget > direction) {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < direction) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10, width, height);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration, int width, int height) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = x + velocity / angularVelocity *
                (Math.sin(direction + angularVelocity * duration) -
                        Math.sin(direction));
        if (!Double.isFinite(newX)) {
            newX = x + velocity * duration * Math.cos(direction);
        }
        double newY = y - velocity / angularVelocity *
                (Math.cos(direction + angularVelocity * duration) -
                        Math.cos(direction));
        if (!Double.isFinite(newY)) {
            newY = y + velocity * duration * Math.sin(direction);
        }
        newX = applyLimits(newX, 15, width - 15);
        newY = applyLimits(newY, 15, height - 15);
        x = newX;
        y = newY;
        double newDirection = asNormalizedRadians(direction + angularVelocity * duration);
        direction = newDirection;
        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

}
