package AntiTD.GameBackEnd;

import AntiTD.GraphicalUserInterface.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

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
        newGame.setBackground(Color.white);
        restartGame.setBackground(Color.white);
        pauseGame.setBackground(Color.white);
        mute.setBackground(Color.white);
        exitGame.setBackground(Color.white);

                newGame.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        gui.startGame();
                        mute.setText("Mute");
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
                    Environment.pauseGame();
                    pauseGame.setText("Resume");
                    gui.pauseMusic();
                    pause=false;
                }
                else{
                    Environment.resumeGame();
                    pauseGame.setText("Pause");
                    gui.resumeMusic();
                    pause=true;
                }
            }
        });
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(mutesound){
                    gui.pauseMusic();
                    mute.setText("Unmute");
                    mutesound=false;
                }
                else {
                    gui.resumeMusic();
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
        help.setBackground(Color.white);
        nameChange.setBackground(Color.white);
        about.setBackground(Color.white);

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                callHelpFrame();
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "SPELET ÄR SKAPAT AV ELIAS","About",1);

            }
        });
        nameChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.changeName(JOptionPane.showInputDialog("Enter name"));
            }
        });

        statMenuBar.add(statmenu);
    }

    private void callHelpFrame(){
        helpPanel = new JPanel();
        helpPanel.setBackground(Color.blue);
        Font font = new Font("Verdana",Font.BOLD,25);
        //textfältet
        helpText = new JTextArea(15,15);
        helpText.setFont(font);
        helpText.setForeground(Color.BLACK);
        helpText.setEditable(false);
        helpText.setWrapStyleWord(true);
        helpText.setLineWrap(true);
        helpText.setBackground(Color.yellow);
        helpText.append("Click the left mouse button to buy troops");
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
