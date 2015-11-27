package AntiTD;

import javax.swing.*;
import AntiTD.*;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class GUI {
    private JFrame frame;
    private Menu menu;
    private GameBoard gameBoard;
    private Thread gameThread;
    private Environment env;

    public GUI() {

        env = new Environment();
        gameThread = new Thread(env);
        gameThread.start();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new JFrame("AntiTTD");

                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        gameThread.interrupt();
                        System.exit(0);
                    }
                });

                menu = new Menu();
                frame.add(menu);

                frame.setVisible(true);
                frame.pack();
                startGame();


            }
        });
    }

    public void startGame() {
        gameBoard = new GameBoard();
        frame.remove(menu);
        frame.add(gameBoard, BorderLayout.CENTER);
        }
}
