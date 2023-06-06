package uptc.edu.co.presenter;
import uptc.edu.co.model.PlaneManagerModel;
import uptc.edu.co.view.MyFrame;
public class ManagerGeneral {
    ContractPlanes.View view;
    ContractPlanes.Model modelPlanes;
    ContractPlanes.Presenter presenter;
    public ManagerGeneral(){
        runProject();
    }
    private void createMVP(){
        modelPlanes = new PlaneManagerModel();
        presenter = new Presenter(this);
        view = new MyFrame();
        view.setPresenter(presenter);
        modelPlanes.setPresenter(presenter);
        presenter.setView(view);
        presenter.setModel(modelPlanes);
    }
    public void runProject(){
        createMVP();
        view.start();
        modelPlanes.starThread();
        modelPlanes.startMovements();
        modelPlanes.countSegund();
    }
}
