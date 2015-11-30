package AntiTD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by mattias on 2015-11-27.
 */
public class Menu extends JMenu {

    private JMenuItem newGame,restartGame;
    private JMenuBar startMenuBar = new JMenuBar();
    private JFrame frame;
    private GUI gui;

    public Menu(JFrame frame, GUI gui) {
        super("Start");
        this.gui = gui;
        this.frame = frame;
    }



    public void startMenu(){
        frame.setJMenuBar(startMenuBar);
        startMenuBar.add(this);
        //lägga till menyitems
        newGame = this.add("New Game");
        restartGame = this.add("Restart");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.startGame();
            }

        });
        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.restartGame();
            }
        });

        startMenuBar.add(this);
    }
    public void scoreScreen(){

    }

}
