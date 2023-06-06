package uptc.edu.co.view;
import uptc.edu.co.pojos.Plane;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlaneManagerCreator extends JPanel {
    private java.util.List<Plane> plane;
    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;
    private JPopupMenu jPopupMenu;
    private Boolean pause = true;
    private MyFrame myFrame;
    private Image imagen;
    private Plane planetmp = null;
    public PlaneManagerCreator(MyFrame myFrame){
        this.myFrame = myFrame;
        requestFocusInWindow();
        setFocusable(true);
        Controler();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseMotionListener);
        setBackground(Color.black);
        runre();
        initComponents();
    }
    public void initComponents(){
        jPopupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Cambiar Color");
        JMenuItem menuItem2 = new JMenuItem("Cambiar velocidad");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.getOptionColor().setVisible(true);
            }
        });
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.initOptionSpeed();
                myFrame.getOptionSpeed().setVisible(true);
            }
        });
        jPopupMenu.add(menuItem1);
        jPopupMenu.add(menuItem2);
        try {
            imagen = ImageIO.read(new File("resource/pista.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setPlane(java.util.List<Plane> plane){
        this.plane = plane;
    }
    public void paint(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(imagen, 300, 260, 200, 40, null);
        try {
            for (int i = 0; i < plane.size(); i++) {
                g2d.setFont(new Font(null, 100, 20));
                paintData(g2d);
                g2d.setColor(plane.get(i).getColor());
                g2d.setFont(new Font(null, 100, 50));
                paintPlane(g2d, plane.get(i));
            }
        }catch (Exception e){}
    }
    public void paintData(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.drawString("# Aviones: " + plane.size(), 5, 20);
        g2d.drawString("Tiempo: "+ myFrame.getCountSegund(), 150, 20);
        g2d.drawString("Aviones Aterrizados: "+ myFrame.getPresenter().getWins(), 350, 20);
        g2d.drawString("Puntos: "+ myFrame.getPresenter().getTerrifiedPlanes(), 600, 20);
    }
    public void paintPlane(Graphics2D g2d, Plane plane){
        double angleDegrees = Math.toDegrees(plane.getDirection());
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform newTransform = (AffineTransform) oldTransform.clone();
        newTransform.rotate(Math.toRadians(angleDegrees), plane.getX(), plane.getY());
        g2d.setTransform(newTransform);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth("✈");
        int textHeight = fontMetrics.getHeight();
        int textX = (int) Math.round(plane.getX()) - textWidth / 2;
        int textY = (int) Math.round(plane.getY()) - textHeight / 2 + fontMetrics.getAscent();
        g2d.drawString("✈", textX, textY);
        g2d.setTransform(oldTransform);
        paintPoints(g2d, plane);
        g2d.setColor(Color.WHITE);
    }
    public void paintPoints(Graphics2D g2d, Plane plane) {
        g2d.setColor(plane.getColor());
        ArrayList<Point> points = plane.getPointsP();
        for (Point point : points) {
            int x = (int) Math.round(point.getX());
            int y = (int) Math.round(point.getY());
            g2d.fillOval(x, y, 2, 2);
        }
    }
    public void runre(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
    public void setColor(Color color){
        myFrame.getPresenter().setColor(planetmp, color);
    }
    public void setSpeed(int speed){
        myFrame.getPresenter().setSpeed(planetmp, speed);
    }
    public void setPause(Boolean pause){
        myFrame.getPresenter().setPause(pause);
        this.pause = pause;
    }
    public void Controler(){
        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if(planetmp!=null){
                        jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                        pause= true;
                        setPause(false);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                planetmp = myFrame.getPresenter().search(e.getPoint());
                if (planetmp != null && e.getButton() == MouseEvent.BUTTON1) {
                    planetmp.getPointsP().clear();
                }
                setPause(true);
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
        mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                    if (planetmp != null) {
                        myFrame.getPresenter().addPoint(planetmp, e.getPoint());
                    }
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {}
        };
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        String actionKey = "escapeAction";
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, actionKey);
        getActionMap().put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pause) {
                    setPause(false);
                    JPopupMenu popupMenu = new JPopupMenu();
                    popupMenu.setPreferredSize(new Dimension(100, 100));
                    JMenuItem reiniciarMenuItem = new JMenuItem("Reiniciar");
                    reiniciarMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
                    reiniciarMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            myFrame.getPresenter().resStar();
                        }
                    });
                    popupMenu.add(reiniciarMenuItem);
                    JMenuItem salirMenuItem = new JMenuItem("Salir del juego");
                    salirMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
                    salirMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                    popupMenu.add(salirMenuItem);
                    popupMenu.show(myFrame, 380, 250);
                } else {
                    setPause(true);
                }
            }
        });
    }
}