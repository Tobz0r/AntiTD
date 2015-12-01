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
    private GameBoard gameBoard;
    private Thread gameThread;
    private Environment env;
    private JFrame frame;

    public GUI () {
        env = new Environment(this);
        gameBoard=env.getGrid();
        gameThread = new Thread(env);
        gameThread.start();


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
        frame.add(gameBoard, BorderLayout.CENTER);
        frame.setVisible(true);
        gameBoard.repaint();
    }
    public void restartGame(){
        frame.remove(gameBoard);
        frame.setVisible(true);
        gameBoard.resetGame();
    }


}
