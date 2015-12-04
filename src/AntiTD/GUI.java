package AntiTD;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;

import AntiTD.*;
import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.troops.BasicTroop;
import AntiTD.troops.SpeedTroop;
import AntiTD.troops.TeleportTroops;

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
public class GUI  {
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



    public GUI () {
        env = new Environment();
        frame = new JFrame("AntiTTD");
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


        frame.pack();

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
        buyPanel.setBorder(BorderFactory.createLineBorder(Color.green));
        buyPanel.setBackground(Color.magenta);
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
        //teleport troop button
        buyTeleport = new JButton("Teleport Troop");
        buyTeleport.setBackground(Color.white);
        buyTeleport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap = Level.getCurrentMap();
                env.addTroops(new TeleportTroops(currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
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
        buyPanel.add(buySpeed);
        buyPanel.add(buyTeleport);
        buyPanel.add(buyButton, FlowLayout.LEFT);

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
        //behövs en bättre lösning
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

}
