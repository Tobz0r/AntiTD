package AntiTD;

import javax.swing.*;
import AntiTD.*;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author dv13trm
 */
public class GUI  {
    private Menu menu;
    private Thread gameThread;
    private Environment env;
    private JFrame frame;

    public GUI () {
        env = new Environment();
        env.start();
        frame = new JFrame("AntiTTD");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //menu = new Menu(frame);
        menu = new Menu(frame, this);
        menu.startMenu();

        menu = new Menu(frame, this);
        menu.startMenu();
        menu.statMenu();
        frame.setVisible(true);

    }

    public void startGame() {
        frame.add(env);
        frame.setVisible(true);

    }
    public void restartGame(){
        //restart
    }


}
