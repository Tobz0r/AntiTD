package AntiTD;


import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import AntiTD.*;
import AntiTD.tiles.CrossroadTile;
import AntiTD.tiles.JunctionTile;
import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.towers.BasicTower;
import AntiTD.towers.FrostTower;
import AntiTD.troops.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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
    private JButton crossButton;
    private JButton buySpeed;
    private JButton buyTank;
    private JButton teleportButton;
    private Thread thread;
    //startscreen
    private String PlayerName;
    private JTextArea player;
    private JButton enterName;
    private JLabel tenChars;

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
    //Unit images
    private BufferedImage basicImage;
    private BufferedImage speedImage;
    private BufferedImage tankImage;
    private BufferedImage teleporterImage;
    private TeleportTroop teleportTroop=null;



    public GUI () {

        env = new Environment(this);
        try {
            basicImage= ImageIO.read(new File("sprites/ogre.gif"));
            speedImage = ImageIO.read(new File("sprites/redDragon.gif"));
            tankImage = ImageIO.read(new File ("sprites/earthElemental.gif"));
            teleporterImage = ImageIO.read(new File("sprites/Teleporter.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        //Handler.clearList();
        env.stop();
        env.isGameOver();
        env = new Environment(this);
        startGame();
    }

    private void buildBuyPanel(){
        buyPanel = new JPanel();
        printScore();
        buyPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buyPanel.setBackground(Color.black);
        buyPanel.setPreferredSize(new Dimension(50,75));
        GridLayout layout = new GridLayout(4,2);
        buyPanel.setLayout(layout);
        layout.setHgap(2);
        layout.setVgap(2);

        teleportButton = new JButton("Set Teleporter");
        //basictropp button
        buyButton = new JButton("Small Ogre");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap = Level.getCurrentMap();
                env.addTroop(new BasicTroop(basicImage, currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
                //env.addTroops(new BasicTroop(currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
            }
        });
        //basictropp button
        buyTank= new JButton("Earth Elemental");
        buyTank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap = Level.getCurrentMap();
                env.addTroop(new TankTroop(tankImage,currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
            }
        });
        printScore();
        //Testar torn
        buyTeleport = new JButton("Teleporter");
        buyTeleport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap = Level.getCurrentMap();
                teleportTroop=new TeleportTroop(teleporterImage,currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]);
                env.addTroop(teleportTroop);
                teleportButton.setEnabled(true);
            }
        });
        buySpeed = new JButton("Speed Demon");
        buySpeed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap = Level.getCurrentMap();
                env.addTroop(new SpeedTroop(speedImage, currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
                //env.addTroops(new SpeedTroop(currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
            }
        });
        crossButton = new JButton("Change direction");
        crossButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.print("eliashej");
            }
        });

        teleportButton.setEnabled(false);
        teleportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile tile = teleportTroop.getTilePosition();
                if (teleportTroop.isAlive()) {
                    if (!(tile instanceof CrossroadTile) && !(tile instanceof JunctionTile)) {
                        teleportTroop.initTeleport();
                        teleportButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Teleporters can't be placed on crossroads");
                    }
                } else {
                    teleportButton.setEnabled(false);
                }
            }
        });



        buyPanel.add(score);
        buyPanel.add(money);
        buyPanel.add(buySpeed);
        buyPanel.add(buyTeleport);
        buyPanel.add(buyButton);
        buyPanel.add(buyTank);
        buyPanel.add(crossButton);
        buyPanel.add(teleportButton);

        frame.add(buyPanel, BorderLayout.SOUTH);
    }
    public void getName(){
        PlayerName=player.getText();
    }
    void changeName(String name){
        PlayerName=name;
    }



    public void startScreen()  {
        //check to see if panel still exists
        if(buyPanel !=null){
            frame.remove(buyPanel);
        }
        tenChars = new JLabel("Max 11 character");
        env.stop();
        frame.remove(scrollPane);
        player = new JTextArea(textCols, textRows);
        player.setEditable(true);
        player.setWrapStyleWord(true);
        player.setLineWrap(true);
        playerScroll = new JScrollPane(player);
        player.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(tenChars);
        startPanel = new JPanel();
        startPanel.setBackground(Color.white);
        startPanel.add(playerScroll, BorderLayout.CENTER);
        enterName = new JButton("Submit name");
        enterName.setBackground(Color.pink);
        startPanel.add(enterName, FlowLayout.LEFT);
        startPanel.add(tenChars);
        checkTextField();
        frame.setSize(300, 200);
        frame.add(startPanel);
        frame.setVisible(true);
        enterName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(player.getDocument().getLength()!=0){
                    getName();
                    startGame();
                }
            }
        });

        
    }
    private void checkTextField(){

        player.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                Document doc = player.getDocument();
                if (doc.getLength() > 10) {
                    player.setEditable(false);
                    player.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent keyEvent) {

                        }

                        @Override
                        public void keyPressed(KeyEvent keyEvent) {
                            backSpace(keyEvent);
                        }

                        @Override
                        public void keyReleased(KeyEvent keyEvent) {

                        }
                    });
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if (player.getDocument().getLength() > 10) {
                    player.setEditable(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                if (player.getDocument().getLength() > 10) {
                    player.setEditable(false);
                }
            }
        });

    }

    private void backSpace(KeyEvent k){
        int i = 0;
        if(k.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            player.setEditable(true);
            if(!k.isConsumed()){
                k.consume();
                i++;
            }
            Document doc = player.getDocument();
            if(doc.getLength()>0 && doc.getLength() <12 ){
                try {
                    doc.remove(doc.getLength()-1, i);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }

            }

        }
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

    }

    public void updateScore(){
        score.setText("Score:"+String.valueOf(env.getScore()));
        money.setText("Money");
        score.setColumns(5);
    }
}
