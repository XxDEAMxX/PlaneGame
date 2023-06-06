package uptc.edu.co.pojos;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Plane {
    private int id;
    private double x;
    private double y;
    private double speed;
    private double direction;
    private double positionX;
    private double positionY;
    private ArrayList<Point> pointsP;
    private Color color;
    private boolean input;
    private boolean output;
    public Plane(){
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        color = new Color(red, green, blue);
        input = false;
        output = false;
    }
    public void calculos() {
        double dx = 400 - x;
        double dy = 300 - y;
        direction = Math.atan2(dy, dx);
        speed = 1;
        pointsP = new ArrayList<>();
    }

    public void pointSpeed(){
        ArrayList<Point> intermediatePoints = new ArrayList<>();

        for (int i = 0; i < pointsP.size() - 1; i++) {
            Point point1 = pointsP.get(i);
            Point point2 = pointsP.get(i + 1);
            double distance = point1.distance(point2);
            double numberOfPoints = distance / getSpeed();
            double xIncrement = (point2.x - point1.x) / numberOfPoints;
            double yIncrement = (point2.y - point1.y) / numberOfPoints;
            for (int j = 1; j < numberOfPoints; j++) {
                int x = (int) (point1.x + j * xIncrement);
                int y = (int) (point1.y + j * yIncrement);
                intermediatePoints.add(new Point(x, y));
            }


        }
        setPointsP(intermediatePoints);
    }

    public boolean isInput() {
        return input;
    }
    public void setInput(boolean input) {
        this.input = input;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Point> getPointsP() {
        return pointsP;
    }

    public void setPointsP(ArrayList<Point> points) {
        this.pointsP = points;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        pointSpeed();
        this.speed = speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
}
