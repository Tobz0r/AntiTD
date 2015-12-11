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
    private File fp;
    private Thread gameThread;
    private Environment env;
    private JFrame frame;
    private JPanel buyPanel;
    private JButton ogreButton;
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
    private JLabel title;
    private JPanel titlePanel;

    private StartScreen startPanel;
    private JScrollPane playerScroll;
    private JScrollPane scrollPane;
    private static final int textRows = 10;
    private static final int textCols = 1;
    //sound
    private Sounds sounds = new Sounds();
    //score
    private JTextField score;
    private JTextField money;
    //Unit images
    private BufferedImage basicImage;
    private BufferedImage speedImage;
    private BufferedImage tankImage;
    private BufferedImage teleporterImage;
    private TeleportTroop teleportTroop=null;
    //highscore
    private JTable scoreTable;


    public GUI (File fp) {
        this.fp=fp;
        env = new Environment(this,fp);
        try {
            basicImage= ImageIO.read(new File("sprites/ogre.gif"));
            speedImage = ImageIO.read(new File("sprites/redDragon.gif"));
            tankImage = ImageIO.read(new File ("sprites/earthElemental.gif"));
            teleporterImage = ImageIO.read(new File("sprites/Teleporter.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        frame = new JFrame("AntiTD");

        ImageIcon img = new ImageIcon("sprites/icon.png");
        frame.setIconImage(img.getImage());

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
        if(!sounds.isPlaying())
            sounds.music("music/cello.wav",true,false);
        frame.remove(startPanel);
        frame.remove(titlePanel);
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
        env.restartLevel(true);
    }
    public void pauseMainSound(){
        sounds.pauseMusic();
    }
    public void resumeMainSound(){
        sounds.resumeMusic(true);
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
        ogreButton = new JButton("Small Ogre $175");
        ogreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(env.buyUnit(175)) {
                    Tile[][] currentMap = Level.getCurrentMap();
                    env.addTroop(new BasicTroop(basicImage, currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
                }
                }
        });
        //basictropp button
        buyTank= new JButton("Earth Elemental $450");
        buyTank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(env.buyUnit(450)) {
                    Tile[][] currentMap = Level.getCurrentMap();
                    env.addTroop(new TankTroop(tankImage, currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
                }
            }
        });
        printScore();
        //Testar torn
        buyTeleport = new JButton("Teleporter $4000");
        buyTeleport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(env.buyUnit(4000)) {
                    Tile[][] currentMap = Level.getCurrentMap();
                    teleportTroop = new TeleportTroop(teleporterImage, currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]);
                    env.addTroop(teleportTroop);
                    teleportButton.setEnabled(true);
                }
            }
        });
        buySpeed = new JButton("Speed Demon $325");
        buySpeed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap = Level.getCurrentMap();
               if(env.buyUnit(325))
                   env.addTroop(new SpeedTroop(speedImage, currentMap[env.getLevel().getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
            }
        });
        crossButton = new JButton("Change direction");
        crossButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tile[][] currentMap = Level.getCurrentMap();
                for(int i=0; i < currentMap.length; i++ ){
                    for(int j=0; j < currentMap[0].length; j++){
                        if( currentMap[i][j]instanceof CrossroadTile){
                            try {
                                ((CrossroadTile)currentMap[i][j]).changeWay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
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
        buyPanel.add(ogreButton);
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
        sounds.music("music/start.wav",true,false);
        tenChars = new JLabel("Max 11 character");
        title = new JLabel("Anti TD");
        fixTitle(title);
        env.stop();
        frame.remove(scrollPane);
        player = new JTextArea(textCols, textRows);
        player.setEditable(true);
        player.setWrapStyleWord(true);
        player.setLineWrap(true);
        playerScroll = new JScrollPane(player);
        player.setBorder(BorderFactory.createLineBorder(Color.black));
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.cyan);
        startPanel = new StartScreen();
        startPanel.repaint();
        startPanel.add(playerScroll, BorderLayout.CENTER);
        enterName = new JButton("Submit name");
        enterName.setBackground(Color.pink);
        startPanel.add(enterName, FlowLayout.LEFT);
        titlePanel.add(title);
        startPanel.add(tenChars);
        checkTextField();
        frame.setSize(400, 300);
        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(startPanel);

        frame.setVisible(true);
        enterName.setBackground(Color.WHITE);
        enterName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(player.getDocument().getLength()!=0){
                    sounds.pauseMusic();
                    menu.setNewGame("Restart");
                    getName();
                    startGame();
                }
            }
        });

        
    }
    private void fixTitle(JLabel title){
        Font lableFont = title.getFont();
        int biggerFont = (int)(lableFont.getSize() * 50);
        int fontSizeUse = Math.min(biggerFont, 30);
        title.setFont(new Font(lableFont.getName(),Font.PLAIN,fontSizeUse));
        title.setForeground(Color.white);
    }
    private class StartScreen extends JPanel{
        Image bg = new ImageIcon("sprites/full_background.png").getImage();
        @Override
        public void paintComponent(Graphics g){
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);
        }

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
        money.setText("Money"+String.valueOf(env.getMoney()));
        score.setColumns(5);
    }
    public void highScoreTable(){
        scoreTable = new JTable(10,3);

    }

}

