package uptc.edu.co.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class OptionColor extends JDialog {
    private JColorChooser colorChooser;
    private JButton button;
    private MyFrame myFrame;
    public OptionColor(MyFrame myFrame){
        this.myFrame = myFrame;
        getContentPane().setBackground(new Color(236, 237, 241));
        setLayout(new GridBagLayout());
        setTitle("Color");
        setSize(700,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponent();
        setVisible(false);
    }
    public void initComponent(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        colorChooser = new JColorChooser();
        add(colorChooser, gbc);
        button = new JButton("aceptar");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.getPlaneManagerCreator().setColor(colorChooser.getColor());
                setVisible(false);
                myFrame.getPlaneManagerCreator().setPause(true);
            }
        });
        gbc.gridy = 1;
        add(button, gbc);
    }
}
