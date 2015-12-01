package AntiTD;

import javax.swing.*;
import AntiTD.*;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author dv13trm
 */
public class GUI implements Observer{
    private Menu menu;
    private GameBoard gameBoard;
    private Thread gameThread;
    private Environment env;
    private JFrame frame;

    public GUI () {
        int i=1;
        env = new Environment(this);
        gameThread = new Thread(env);
        gameThread.start();


                frame = new JFrame("AntiTTD");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //menu = new Menu(frame);
        menu = new Menu(frame, this);
        menu.startMenu();

                menu = new Menu(frame, this);
                menu.startMenu();
                menu.statMenu();
                frame.setVisible(true);
                frame.pack();




    }


    public void startGame() {
        frame.add(gameBoard, BorderLayout.CENTER);
    }
    public void restartGame(){
        frame.remove(gameBoard);
        gameBoard.resetGame();
    }

    @Override
    public void update(Observable observable, Object o) {
        gameBoard=(GameBoard) o;
    }
}
