package AntiTD;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.SpringLayout;

import AntiTD.*;
import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.towers.BasicTower;
import AntiTD.troops.BasicTroop;
import AntiTD.troops.SpeedTroop;
import AntiTD.troops.Troop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * @author dv13trm
 */
public class GUI {

    ImageIcon img = new ImageIcon("/home/id12/id12rdt/basictower.png");
    private Menu menu;
    private Thread gameThread;
    private Environment env;
    private JFrame frame;
    private JPanel buyPanel;
    private JButton buyButton;
    private JButton buyTeleport;
    private JButton buySpeed;
    private Thread thread;
    //startscreen
    private String PlayerName;
    private JTextArea player;
    private JButton enterName;
    private JPanel startPanel;
    private JScrollPane playerScroll;
    private JScrollPane scrollPane;
    private static final int textRows = 10;
    private static final int textCols = 1;
    //sound
    private String gameSound;
    Clip clip = null;
    long clipTime;
    //score
    private JTextField score;
    private JTextField money;



    public GUI () {

        env = new Environment(this);

        frame = new JFrame("AntiTD");
         scrollPane = new JScrollPane(env);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setBounds(0,0,env.getWidth()+32,env.getHeight()+32);

        startScreen();
        //menu = new Menu(frame);
        menu = new Menu(frame, this);
        menu.startMenu();
        menu.statMenu();

        frame.setVisible(true);




    }

    public void startGame() {
        runMusic();
        frame.remove(startPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane, BorderLayout.CENTER);
        env.start();
        env.repaint();
        buildBuyPanel();
        frame.pack();

    }
    public void restartGame(){
        //ta bort alla torn och teleportertiles
        Handler.clearList();
        env.stop();
        startGame();
    }

    private void buildBuyPanel(){

        buyPanel = new JPanel();
        printScore();
        buyPanel.setBorder(BorderFactory.createLineBorder(Color.green));
        buyPanel.setBackground(Color.magenta);
        buyPanel.setPreferredSize(new Dimension(50,75));

        
        //basictropp button
        buyButton = new JButton("Basic troops");
        buyButton.setBackground(Color.white);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap= Level.getCurrentMap();
                env.addTroops(new BasicTroop(currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
            }
        });

        //Testar torn
        buyTeleport = new JButton("Teleport Troop");
        buyTeleport.setBackground(Color.white);
        buyTeleport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("TELEPORTELIAS");
                Tile pos;
                Tile[][] currentMap = Level.getCurrentMap();
                for (int i = 0; i < currentMap.length; i++) {
                    for (int j = 0; j < currentMap[i].length; j++) {
                        if (currentMap[i][j].isBuildable()) {
                            //pos = currentMap[i][j];
                            env.addTower(new BasicTower(img, currentMap[i][j], env.getTroops()));
                            currentMap[i][j].setBuildable(false);
                        }

                    }
                }
                /*env.saveBuildableTilese();
                env.addTower(new BasicTower(img, env.getBuildAbleTile(5)))*/
                ;
                //env.addTower(new BasicTower(currentMap[env.getLevel().]);
                //env.addTroops(new Dummy(null)); //la in en dummy för att testa trådning
            }
        });
        buySpeed = new JButton("Speed Troop");
        buySpeed.setBackground(Color.white);
        buySpeed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap = Level.getCurrentMap();
                env.addTroops(new SpeedTroop(currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
            }
        });



        buyPanel.add(buySpeed,BorderLayout.EAST);
        buyPanel.add(buyTeleport,BorderLayout.CENTER);
        buyPanel.add(buyButton, BorderLayout.CENTER);
        frame.add(buyPanel, BorderLayout.SOUTH);
    }
    public void getName(){
        PlayerName=player.getText();
    }
    void changeName(String name){
        PlayerName=name;
    }


    public void startScreen()  {
        env.stop();
        frame.remove(scrollPane);
        player = new JTextArea(textCols, textRows);
        //behövs en bättre lösning <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        player.setEditable(true);
        player.setWrapStyleWord(true);
        player.setLineWrap(true);

        playerScroll = new JScrollPane(player);

        player.setBorder(BorderFactory.createLineBorder(Color.black));

        startPanel = new JPanel();
        startPanel.setBackground(Color.white);
        startPanel.add(playerScroll, BorderLayout.CENTER);
        enterName = new JButton("Submit name");
        enterName.setBackground(Color.pink);
        startPanel.add(enterName, FlowLayout.LEFT);
        frame.setSize(300, 200);
        frame.add(startPanel);
        frame.setVisible(true);
        enterName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                getName();
                startGame();
            }
        });

    }
    public void runMusic()  {
        gameSound = "cello.wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(gameSound));
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    public void pauseMusic(){
        clipTime = clip.getMicrosecondPosition();
        clip.stop();
    }
    public void resumeMusic(){
        clip.setMicrosecondPosition(clipTime);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }
    public void printScore(){
        String currentScore;
        String currentMoney;
        currentMoney=String.valueOf(0);
        currentScore=String.valueOf(0);
        money = new JTextField();
        money.setEditable(false);
        money.setBackground(Color.white);
        money.setBorder(null);
        score = new JTextField();
        score.setEditable(false);
        score.setBackground(Color.white);
        score.setBorder(null);
        score.setText(currentScore);
        buyPanel.add(score);
        buyPanel.add(money);
    }

    public void updateScore(){
        String scoreValue=String.valueOf(Troop.getVictoryScore());
        score.setText(scoreValue);
        score.setColumns(scoreValue.length() + 1);
        money.setText(scoreValue);
        money.setColumns(scoreValue.length()+1);
    }

}
