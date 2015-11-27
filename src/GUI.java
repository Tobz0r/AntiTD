import javax.swing.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class GUI {
    private JFrame frame;

    public GUI() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new JFrame("AntiTTD");

                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        System.exit(0);
                    }
                });

                JPanel menu = new Menu();
                frame.add(menu);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
