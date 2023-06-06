package uptc.edu.co.view;
import uptc.edu.co.pojos.Plane;
import uptc.edu.co.presenter.ContractPlanes;
import javax.swing.*;
import java.util.List;
public class MyFrame extends JFrame implements ContractPlanes.View {
    private ContractPlanes.Presenter presenter;
    private PlaneManagerCreator planeManagerCreator;
    private OptionColor optionColor;
    private OptionSpeed optionSpeed;
    public MyFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        initComponent();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    public void initComponent() {
        planeManagerCreator = new PlaneManagerCreator(this);
        add(planeManagerCreator);
        optionColor = new OptionColor(this);
    }
    public void initOptionSpeed() {
        optionSpeed = new OptionSpeed(this);
    }
    public PlaneManagerCreator getPlaneManagerCreator() {
        return planeManagerCreator;
    }
    public OptionColor getOptionColor() {
        return optionColor;
    }
    public OptionSpeed getOptionSpeed() {
        return optionSpeed;
    }
    public ContractPlanes.Presenter getPresenter() {
        return presenter;
    }
    @Override
    public void setPresenter(ContractPlanes.Presenter presenter) {
        this.presenter = presenter;
    }
    @Override
    public void start() {
        setVisible(true);
    }
    @Override
    public void setPlane(List<Plane> planes) {
        planeManagerCreator.setPlane(planes);
    }
    @Override
    public String getCountSegund() {
        return presenter.getCountSegund();
    }
    @Override
    public void repaintR() {
        planeManagerCreator.repaint();
    }
}
