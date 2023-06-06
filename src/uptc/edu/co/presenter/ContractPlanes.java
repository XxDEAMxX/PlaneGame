package uptc.edu.co.presenter;
import uptc.edu.co.pojos.Plane;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public interface ContractPlanes {
    public interface View{
        void setPresenter(ContractPlanes.Presenter presenter);
        void start();
        void setPlane(List<Plane> planes);
        String getCountSegund();
        void repaintR();
    }
    public interface Model{
        void setPresenter(ContractPlanes.Presenter presenter);
        ArrayList<Plane> getPlanesArray();
        void setPlanes();
        void starThread();
        void mover();
        void setPause(Boolean pause);
        void startMovements();
        Plane search (Point point);
        void addPoint(Plane plane, Point point);
        void setColor(Plane plane, Color color);
        void setSpeed(Plane plane, int speed);
        String getCronometro();
        void countSegund();
        int getWins();
        int getTerrifiedPlanes();
        void resStar();
    }
    public interface Presenter{
        void setView(ContractPlanes.View view);
        void setModel(ContractPlanes.Model model);
        ArrayList<Plane> getPlanesArray();
        void setPlanes(List<Plane> planes);
        void setPause(Boolean pause);
        Plane search (Point point);
        void addPoint(Plane plane, Point point);
        void setColor(Plane plane, Color color);
        void setSpeed(Plane plane, int speed);
        String getCountSegund();
        int getWins();
        int getTerrifiedPlanes();
        void resStar();
        void repaintR();;
    }
}
