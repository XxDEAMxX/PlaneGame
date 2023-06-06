package uptc.edu.co.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class OptionSpeed extends JDialog {
    private JSlider slider;
    private MyFrame myFrame;
    private JButton button;
    public OptionSpeed(MyFrame myFrame){
        this.myFrame = myFrame;
        getContentPane().setBackground(new Color(236, 237, 241));
        setLayout(new GridBagLayout());
        setTitle("Velocidad");
        setSize(371,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponent();
        setVisible(false);
    }
    public void initComponent(){
        slider = new JSlider();
        slider.setMinimum(2);
        slider.setMaximum(5);
        slider.setValue(2);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(2);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        button = new JButton("Aceptar");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                myFrame.getPlaneManagerCreator().setSpeed(slider.getValue());
                myFrame.getPlaneManagerCreator().setPause(true);
                dispose();
            }
        });
        add(slider);
        add(button);
    }
}
