package AntiTD;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author dv13trm
 */
public class Menu extends JMenu {
    //startmenu
    private JMenuItem newGame,restartGame, exitGame, pauseGame, mute;
    private JMenuBar startMenuBar = new JMenuBar();
    private JFrame frame;
    private GUI gui;
    private boolean pause = true;
    private boolean mutesound = true;
    //helpframe
    private JTextArea helpText;
    private JFrame helpFrame = new JFrame();
    private JScrollPane helpScroll;
    private JButton helpButton;
    private JPanel helpPanel;


    //statmenu
    private JMenu statmenu = new JMenu("Help");
    private JMenuItem  nameChange, about, help;
    private JMenuBar statMenuBar = new JMenuBar();





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
        pauseGame = this.add("Pause");
        mute = this.add("Mute");
        exitGame = this.add("Quit");
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
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        pauseGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(pause){
                    //sätta nån loop variabel till falsk?
                    pauseGame.setText("Resume");
                    pause=false;
                }
                else{
                    //sätta nån loop variabel till sann?
                    pauseGame.setText("Pause");
                    pause=true;
                }
            }
        });
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(mutesound){
                    //muta ljudet
                    mute.setText("Unmute");
                    mutesound=false;
                }
                else {
                    //unmuta ljudet
                    mute.setText("Mute");
                    mutesound = true;
                }
            }
        });


        startMenuBar.add(this);
    }

    public void statMenu(){
        frame.setJMenuBar(statMenuBar);
        statMenuBar.add(this);

        //lägga till menyitems
        help = statmenu.add("Help");
        about = statmenu.add("About");
        nameChange = statmenu.add("Change name");

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                callHelpFrame();
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "SPELET ÄR SKAPAT AV ELIAS");
            }
        });
        nameChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        statMenuBar.add(statmenu);
    }

    private void callHelpFrame(){
        helpPanel = new JPanel();
        helpPanel.setBackground(Color.blue);
        //textfältet
        helpText = new JTextArea(15,15);
        helpText.setEditable(false);
        helpText.setWrapStyleWord(true);
        helpText.setLineWrap(true);
        helpText.setBackground(Color.yellow);
        helpText.append("Här ska det stå hjälp texten");
        //knappen
        helpButton = new JButton("Close");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                helpFrame.dispatchEvent(new WindowEvent(helpFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        helpPanel.add(helpButton);

        helpFrame.setSize(1000, 1000);
        helpFrame.add(helpText);
        helpScroll = new JScrollPane(helpText);
        helpFrame.add(helpScroll, BorderLayout.CENTER);

        helpFrame.add(new JLabel(new ImageIcon("tobiashej.jpg")),BorderLayout.NORTH);
        helpFrame.getContentPane().setBackground(Color.yellow);
        helpFrame.add(helpPanel,BorderLayout.SOUTH);
        helpFrame.setVisible(true);
    }


}

