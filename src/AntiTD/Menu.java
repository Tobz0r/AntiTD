package AntiTD;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.ObjectView;
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
    private JMenuItem newGame,mainMenu, exitGame, pauseGame, mute, highScore;
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
    private JTable priceTable;


    //statmenu
    private JMenu statmenu = new JMenu("Help");
    private JMenuItem  nameChange, about, help;
    private JMenuBar statMenuBar = new JMenuBar();





    public Menu(JFrame frame, GUI gui) {
        super("Start");
        this.gui = gui;
        this.frame = frame;
    }


    public void setNewGame(String change) {
        newGame.setText(change);
    }

    public void startMenu(){
        frame.setJMenuBar(startMenuBar);
        startMenuBar.add(this);
        //lägga till menyitems
        newGame = this.add("Restart");
        pauseGame = this.add("Pause");
        mute = this.add("Mute");
        highScore = this.add("High Score");
        mainMenu = this.add("Main Menu");
        exitGame = this.add("Quit");
        newGame.setBackground(Color.white);
        pauseGame.setBackground(Color.white);
        mute.setBackground(Color.white);
        highScore.setBackground(Color.white);
        exitGame.setBackground(Color.white);
        mainMenu.setBackground(Color.white);
        if(!Environment.isRunning()){
            newGame.setText("New Game");
        }
        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.highScoreTable();
            }
        });
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Environment.isRunning()) {
                    gui.restartGame();
                    mute.setText("Mute");
                    newGame.setText("Restart");
                } else {
                    gui.startGame();
                    newGame.setText("Restart");
                    mute.setText("Mute");
                }

            }

        });
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                newGame.setText("New Game");
                gui.startScreen();
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
                JOptionPane.showMessageDialog(null, "Game created by:\n Thom Renström \n Tobias Estefors \n Rasmus Dahlkvist \n Mattias Edin","About",1);

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
        //ogre
        BufferedImage ogre = null;
        try {
            ogre = ImageIO.read(new File("sprites/ogre.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ogre= (BufferedImage) resizeImage(ogre,25,25);
        ImageIcon ogree= new ImageIcon(ogre);


        //dragon
        BufferedImage dragon = null;
        try {
            dragon = ImageIO.read(new File("sprites/redDragon.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dragon= (BufferedImage) resizeImage(dragon,25,25);
        ImageIcon dragonn= new ImageIcon(dragon);


        //earthElemental
        BufferedImage earth = null;
        try {
            earth = ImageIO.read(new File("sprites/earthElemental.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        earth= (BufferedImage) resizeImage(earth,25,25);
        ImageIcon earthh= new ImageIcon(earth);

        //teleporter
        BufferedImage tele = null;
        try {
            tele = ImageIO.read(new File("sprites/Teleporter.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tele= (BufferedImage) resizeImage(tele,50,50);
        ImageIcon telee= new ImageIcon(tele);



        //priceTable
        String[] columns={"Units","Image"};
        Object[][] rows = {{ogre}};
      /*  DefaultTableModel model = new DefaultTableModel(rows, columns) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column){
                    case 0:
                    case 1: return Integer.class;
                    case 2: return ImageIcon.class;
                    default: return Object.class;
                }

            }

        }; */
        priceTable = new JTable(5,2);
        priceTable.setValueAt(ogree, 1,1);









        helpPanel = new JPanel();
        helpPanel.setBackground(Color.yellow);
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

       // helpFrame.getContentPane().setBackground(Color.yellow);
        helpFrame.add(helpPanel, BorderLayout.SOUTH);
        helpFrame.add(priceTable, BorderLayout.CENTER);
        helpFrame.setVisible(true);
    }
    private Image resizeImage(Image myImg, int w, int h){
        BufferedImage resizeImg = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizeImg.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(myImg,0,0,w,h,null);
        g.dispose();

        return resizeImg;
    }



}

