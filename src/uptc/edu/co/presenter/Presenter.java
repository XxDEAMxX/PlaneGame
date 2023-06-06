package uptc.edu.co.presenter;

import uptc.edu.co.pojos.Plane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Presenter implements ContractPlanes.Presenter{
    private ContractPlanes.View view;
    private ContractPlanes.Model model;
    private ManagerGeneral managerGeneral;
    public Presenter(ManagerGeneral managerGeneral){
        this.managerGeneral = managerGeneral;
    }
    @Override
    public void setView(ContractPlanes.View view) {
        this.view = view;
    }
    @Override
    public void setModel(ContractPlanes.Model model) {
        this.model = model;
    }
    @Override
    public ArrayList<Plane> getPlanesArray() {
        return model.getPlanesArray();
    }
    @Override
    public void setPlanes(List<Plane> planes) {
        view.setPlane(planes);
    }
    @Override
    public void setPause(Boolean pause) {
        model.setPause(pause);
    }
    @Override
    public Plane search(Point point) {
        return model.search(point);
    }
    @Override
    public void addPoint(Plane plane, Point point) {
        model.addPoint(plane, point);
    }
    @Override
    public void setColor(Plane plane, Color color) {
        model.setColor(plane, color);
    }
    @Override
    public void setSpeed(Plane plane, int speed) {
        model.setSpeed(plane, speed);
    }
    @Override
    public String getCountSegund() {
        return model.getCronometro();
    }
    @Override
    public int getWins() {
        return model.getWins();
    }
    @Override
    public int getTerrifiedPlanes() {
        return model.getTerrifiedPlanes();
    }
    @Override
    public void resStar() {
        model.resStar();
    }
    @Override
    public void repaintR() {
        view.repaintR();
    }
    public static void main(String[] args) {
        new ManagerGeneral();
    }
}
