
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
//from   ww  w  . j ava  2s.c  o  m
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

/**
 *
 * @author Aluno
 */
public class Prog {

    static int width = 500;
    static int height = 600;
    private static JPanel substrate;
    private static JPanel upperLayer;
    private static Timer timer;

    public static void main(String ags[]) {

        JFrame frame = new JFrame();
        final Color transparentWhite = new Color(255, 255, 255, 127);
        
        //Adiciona Layers
        JLayeredPane layeredPane = new JLayeredPane();

        //Panels
        JPanel mainLayer = new JPanel();
        substrate = new JPanel();
        upperLayer = new JPanel();

        JPasswordField texto = new JPasswordField();

        //Timer 1000= 1 segundo
        timer = new Timer(5000, (ActionEvent e) -> {
            substrate.setVisible(true);
            upperLayer.setVisible(true);
        });

        //Timer de inatividae
        Toolkit.getDefaultToolkit().addAWTEventListener(
                new AWTEventListener() {
            int count;

            @Override
            public void eventDispatched(AWTEvent event) {
                Object source = event.getSource();
                if (source instanceof Component) {
                    Component comp = (Component) source;
                    Window win;
                    if (comp instanceof Window) {
                        win = (Window) comp;
                    } else {
                        win = SwingUtilities.windowForComponent(comp);
                    }
                    if (win == frame) {
                        timer.restart();

                    }
                }
            }
        },
                AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK
                | AWTEvent.MOUSE_MOTION_EVENT_MASK
                | AWTEvent.MOUSE_WHEEL_EVENT_MASK);

        mainLayer.setBounds(0, 0, width, height);

        substrate = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setColor(transparentWhite);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        ActionListener locker = (java.awt.event.ActionEvent e) -> {
            substrate.setVisible(true);
            upperLayer.setVisible(true);
        };

        String pass = "123";

        texto.setColumns(5);
        Font bigFont = texto.getFont().deriveFont(Font.PLAIN, 15f);
        texto.setFont(bigFont);
        ActionListener unlock = (java.awt.event.ActionEvent e) -> {
            //Verificar senha
            if (texto.getText().contentEquals(pass)) {
                substrate.setVisible(false);
                upperLayer.setVisible(false);
                texto.setText("");
            } else {
                JFrame senhaIncorreta = new JFrame();
                JOptionPane.showMessageDialog(senhaIncorreta, "Senha incorreta");
            }
        };

        JButton unlockButton = new JButton("Unlock");
        unlockButton.addActionListener(unlock);
        JButton botaoIncrivel = new JButton("Botao Incrivel");
        
        substrate.setOpaque(false);
        substrate.setBounds(0, 0, width, height);
        substrate.addMouseListener(new MouseAdapter() {
        });
        substrate.setVisible(false);

        upperLayer = new JPanel();
        upperLayer.setBounds(width / 2 - 50, height / 2 - 50, 100, 100);
        upperLayer.setBorder(new LineBorder(Color.BLACK, 1));
        upperLayer.add(texto);
        upperLayer.add(unlockButton);
        upperLayer.setVisible(false);
        mainLayer.add(botaoIncrivel);
        
        layeredPane.setLayout(null);
        layeredPane.setLayer(mainLayer, 0);
        layeredPane.add(mainLayer);
        layeredPane.setLayer(substrate, 1);
        layeredPane.add(substrate);
        layeredPane.setLayer(upperLayer, 2);
        layeredPane.add(upperLayer);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        frame.add(layeredPane);
        frame.setVisible(true);

    }

}
