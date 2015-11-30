package AntiTD;

import javax.swing.*;
import AntiTD.*;

import java.awt.*;

/**
 * @author dv13trm
 */
public class GUI extends JFrame{
    private Menu menu;
    private GameBoard gameBoard;
    private Thread gameThread;
    private Environment env;
    private JFrame frame;

    public GUI () {

        env = new Environment();
        gameThread = new Thread(env);
        gameThread.start();


        frame = new JFrame("AntiTTD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //menu = new Menu(frame);
        menu = new Menu(frame, this);
        menu.startMenu();

        frame.setVisible(true);
        frame.pack();




    }


    public void startGame() {
        gameBoard = new GameBoard();
        frame.add(gameBoard, BorderLayout.CENTER);
        }
    public void restartGame(){
        frame.remove(gameBoard);
        gameBoard.resetGame();
    }
}
