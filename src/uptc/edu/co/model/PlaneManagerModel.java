package uptc.edu.co.model;
import uptc.edu.co.pojos.Plane;
import uptc.edu.co.presenter.ContractPlanes;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
public class PlaneManagerModel implements ContractPlanes.Model {
    private ContractPlanes.Presenter presenter;
    private boolean pause = true;
    private int countPlane = 0;
    private String Cronometro;
    private ArrayList<Plane> planes;
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private int pistaInicioX = 500;
    private int pistaInicioX2 = 450;
    private int pistaFinX = 320;
    private int pistaFinX2 = 370;
    private int pistaInicoY = 250;
    private int pistaFinalY = 300;
    private int wins = 0;
    private int terrifiedPlanes = 0;
    public PlaneManagerModel() {
        planes = new ArrayList<>();
        createPlanes();
    }
    private Plane createLocalPlane() {
        Plane plane = new Plane();
        positionRandomPlane(plane);
        synchronized (planes) {
            plane.setId(countPlane++);
            planes.add(plane);
            plane.calculos();
            planes.notifyAll();
        }
        return plane;
    }
    public void win() {
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).getX() >= pistaInicioX2 && planes.get(i).getX() <= pistaInicioX &&
                    planes.get(i).getY() >= pistaInicoY && planes.get(i).getY() <= pistaFinalY) {
                planes.get(i).setInput(true);
            }
            if (planes.get(i).isInput() && planes.get(i).getX() <= pistaFinX2 && planes.get(i).getX() >=
                    pistaFinX && planes.get(i).getY() >= pistaInicoY && planes.get(i).getY() <= pistaFinalY) {
                wins++;
                terrifiedPlanes = wins * 5;
                planes.remove(i);
            }
        }
    }
    public void positionRandomPlane(Plane plane) {
        Random rand = new Random();
        boolean xOry = rand.nextBoolean();
        boolean minOrMax = rand.nextBoolean();
        if (xOry == false) {
            if (minOrMax == false) {
                double position = Math.random() * 800;
                plane.setY(0);
                plane.setX(position);
                plane.setPositionX(position);
                plane.setPositionY(0);
            } else {
                double position = Math.random() * 800;
                plane.setY(600);
                plane.setX(position);
                plane.setPositionX(position);
                plane.setPositionY(600);
            }
        } else {
            if (minOrMax == true) {
                double position = Math.random() * 600;
                plane.setY(position);
                plane.setX(0);
                plane.setPositionX(0);
                plane.setPositionY(position);
            } else {
                double position = Math.random() * 600;
                plane.setY(position);
                plane.setX(800);
                plane.setPositionX(800);
                plane.setPositionY(position);
            }
        }
    }
    public void createPlanes() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (pause) {
                        createLocalPlane();
                    }
                    try {
                        Thread.sleep(3500);
                        synchronized (planes) {
                            planes.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
    @Override
    public void setPresenter(ContractPlanes.Presenter presenter) {
        this.presenter = presenter;
    }
    @Override
    public ArrayList<Plane> getPlanesArray() {
        return planes;
    }
    @Override
    public void setPlanes() {
        presenter.setPlanes(planes);
    }
    public void starThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    setPlanes();
                    try {
                        Thread.sleep(10);
                        synchronized (planes) {
                            planes.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        });
        thread.start();
    }
    public String getCronometro() {
        return Cronometro;
    }
    public int getTerrifiedPlanes(){
        return terrifiedPlanes;
    }
    public void countSegund(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        seconds++;
                        if (seconds >= 60) {
                            seconds = 0;
                            minutes++;
                            if (minutes >= 60) {
                                minutes = 0;
                                hours++;
                            }
                        }
                        Cronometro = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                        Thread.sleep(1000);
                        synchronized (planes) {
                            planes.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
    @Override
    public int getWins() {
        return wins;
    }
    @Override
    public void resStar() {
        planes.clear();
        wins = 0;
        terrifiedPlanes = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;
        pause= true;
    }
    public void mover() {
        synchronized (planes) {
            try {
                planes.wait(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Iterator<Plane> iterator = planes.iterator();
                while (iterator.hasNext()) {
                    Plane p = iterator.next();
                    double x = p.getX();
                    double y = p.getY();
                    double newX ;
                    double newY ;
                    if (p.getPointsP().size()>1) {
                        newX = p.getPointsP().get(0).getX();
                        newY = p.getPointsP().get(0).getY();
                        if(p.getPointsP().size()>1) {
                            double dx = p.getPointsP().get(1).getX() - p.getPointsP().get(0).getX();
                            double dy = p.getPointsP().get(1).getY() - p.getPointsP().get(0).getY();
                            p.setDirection(Math.atan2(dy, dx));
                        }
                        p.getPointsP().remove(0);
                        if(p.getPointsP().size()==1){
                            p.getPointsP().remove(0);
                        }
                    }else {
                        newX = x + (p.getSpeed() * Math.cos(p.getDirection()));
                        newY = y + (p.getSpeed() * Math.sin(p.getDirection()));
                    }
                    if (newY > 600 || newY < 0 || newX < 0 || newX > 800) {
                        iterator.remove();
                    } else {
                        p.setX(newX);
                        p.setY(newY);
                    }
                }
            planes.notifyAll();
        }
    }
    @Override
    public void setPause(Boolean pause) {
        this.pause = pause;
    }
    public void startMovements(){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if(pause) {
                            mover();
                            crashPlanes();
                            win();
                        }
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            thread.start();
        }
    public void crashPlanes() {
        for (int i = 0; i < planes.size() - 1; i++) {
            Plane plane1 = planes.get(i);
            Rectangle rectangle1 = new Rectangle((int) plane1.getX(), (int) plane1.getY() - 50, 50, 50);
            for (int j = i + 1; j < planes.size(); j++) {
                Plane plane2 = planes.get(j);
                Rectangle rectangle2 = new Rectangle((int) plane2.getX(), (int) plane2.getY() - 50, 50, 50);
                if (rectangle1.intersects(rectangle2)) {
                    planes.remove(j);
                    planes.remove(i);
                    JOptionPane.showMessageDialog(null, "<html>Puntos obtenidos: "
                            +terrifiedPlanes+"<br> Aviones Aterrizados: "+wins+"<br>tiempo de juego: "
                            +getCronometro()+"<html>", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    planes.clear();
                    wins = 0;
                    hours = 0;
                    minutes = 0;
                    seconds = 0;
                    terrifiedPlanes = 0;
                }
            }
        }
    }
    @Override
    public Plane search(Point point) {
        for (Plane p: planes) {
            Rectangle rectangle = new Rectangle((int) p.getX()-25,(int) p.getY()-25, 50, 50);
            if(rectangle.contains(point)){
                return p;
            }
        }
        return null;
    }
    public Plane searchId(int id) {
        for (Plane p: planes) {
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }
    @Override
    public void addPoint(Plane plane, Point point) {
        Plane auxPlane = searchId(plane.getId());
        if(auxPlane != null) {
            fillPoints(auxPlane, point);
        }
    }
    @Override
    public void setColor(Plane plane, Color color) {
        Plane auxPlane = searchId(plane.getId());
        if(auxPlane != null) {
            auxPlane.setColor(color);
        }
    }
    @Override
    public void setSpeed(Plane plane, int speed) {
        Plane auxPlane = searchId(plane.getId());
        if(auxPlane != null) {
            auxPlane.setSpeed(speed);
        }
    }
    public void fillPoints(Plane plane, Point point) {
        if (plane.getPointsP().isEmpty()) {
            plane.getPointsP().add(point);
        }
        try {
            Point currentPoint = plane.getPointsP().get(plane.getPointsP().size() - 1);
            Point nextPoint = point;
            double distance = currentPoint.distance(nextPoint);
            int steps = (int) Math.ceil(distance / plane.getSpeed()); // Calcular el número de pasos basado en la velocidad
            double deltaX = nextPoint.getX() - currentPoint.getX();
            double deltaY = nextPoint.getY() - currentPoint.getY();
            for (int j = 1; j < steps; j++) {
                double factor = (double) j / steps; // Factor de interpolación entre los puntos
                double x = currentPoint.getX() + deltaX * factor;
                double y = currentPoint.getY() + deltaY * factor;
                plane.getPointsP().add(new Point((int) x, (int) y));
            }
        }catch (Exception e){}
    }
}