package AntiTD;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import AntiTD.database.DBModel;
import AntiTD.tiles.CrossroadTile;
import AntiTD.tiles.JunctionTile;
import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.troops.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author dv13trm
 * GUI class creates an interface for the user
 * It cooperate with every class to show the user all the maps
 * menus, and other things the user can interact with
 */
public class GUI {

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
    private ArrayList<Troop> troops = new ArrayList();
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
        //menu = new Menu(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //menu = new Menu(frame, this,env);
        menu = new Menu(frame, this, env);
        menu.startMenu();
        menu.statMenu();
        startScreen();
        frame.setVisible(true);
    }


    /**
     * Removes the start menu and start the accual game
     */
    public void startGame() {
        if(!sounds.isPlaying()) {
            sounds.music("music/runninggame.wav", true);
            if (menu.musicStatus()) {
                pauseMainSound();
            }
        }

        frame.remove(startPanel);
        frame.remove(titlePanel);
        frame.setSize(800, 600);
        frame.add(scrollPane, BorderLayout.CENTER);
        env.start();
        env.repaint();
        buildBuyPanel();
        frame.pack();
    }

    /**
     * Restarts the current level
     */
    public void restartGame(){
        env.restartLevel(true);
    }

    /**
     * Pauses sound used it gui
     */
    public void pauseMainSound(){
        sounds.pauseMusic();
    }

    /**
     * Resumes sound used in gui
     */
    public void resumeMainSound(){
        sounds.resumeMusic(true);
    }

    /**
     * Create the buy panel where the user can buy units
     * teleportButton sets a teleporter on current tile if pressed
     * butTeleport creates a Teleporter if pressed
     * ogreButton creates a Small Ogre unit if pressed
     * buyTank creates a Earth Elemental unit if pressed
     * buySpeed creates a Speed Demon unit if pressed
     * crossButton changes direction of the crossroad if pressed
     */
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
                    env.addTroop(new BasicTroop(basicImage, currentMap[env.getLevel()
                            .getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));
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
                    env.addTroop(new TankTroop(tankImage, currentMap[env.getLevel()
                            .getStartPosition().getX()][env.getLevel().getStartPosition().getY()]));

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

    /**
     * Gets the name entered in the player textarea
     */
    public void getName(){
        PlayerName=player.getText();
    }

    /**
     * Sets playerName to name entered
     * @param name name user wants to change to
     */
    void changeName(String name){
        PlayerName=name;
    }

    /**
     * @return returns string with players name
     */
    public String getPlayerName(){
        return PlayerName;
    }
    /**
     * Check if music shouldn't be paused if not
     * music will start to play
     */
    public void playMusic(){
        if(!menu.musicStatus()){
            sounds.music("music/start.wav",true);
        }
    }
    /**
     * Creates the main menu that will show when the game is first started
     * It will stop the gameplay if it is up.
     *
     */
    public void startScreen()  {
        //check to see if panel still exists
        if(buyPanel !=null){
            frame.remove(buyPanel);
        }
        playMusic();
        tenChars = new JLabel("Max 11 character");
        title = new JLabel("Anti TD");
        fixTitle(title);
        env.stop();
        frame.remove(scrollPane);
        player = new JTextArea(textCols, textRows);
        player.setEditable(true);
        player.setWrapStyleWord(true);
        player.setLineWrap(true);
        player.setBorder(BorderFactory.createLineBorder(Color.black));
        playerScroll = new JScrollPane(player);
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.cyan);
        startPanel = new StartScreen();
        startPanel.repaint();
        startPanel.add(playerScroll, BorderLayout.CENTER);
        startPanel.add(tenChars);
        enterName = new JButton("Submit name");
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
        startPanel.add(enterName, FlowLayout.LEFT);
        titlePanel.add(title);
        checkTextField();
        frame.setSize(400, 300);
        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(startPanel);
        frame.setVisible(true);
    }

    /**
     * Changes the size and font of an JLable
     * @param title takes a JLable that will be changed
     */
    private void fixTitle(JLabel title){
        Font lableFont = title.getFont();
        int biggerFont = (int)(lableFont.getSize() * 50);
        int fontSizeUse = Math.min(biggerFont, 30);
        title.setFont(new Font(lableFont.getName(),Font.PLAIN,fontSizeUse));
        title.setForeground(Color.white);
    }

    /**
     * New inner class that will put a background on by default
     * Because its overrideing paintcomponent
     */
    private class StartScreen extends JPanel{
        Image bg = new ImageIcon("sprites/full_background.png").getImage();
        @Override
        public void paintComponent(Graphics g){
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);
        }

    }
    /*
     * A text field check, that checks if key pressed
     * is a backspace, if so it will call the backspace function
     * and change how much you can type on the text field
     */
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

    /**
     * Takes a keyevent and if keyevent was a backspace
     * it will remove the last letter or number on the textfield.
     * @param k is what keyevent that will be checked, can be keyTyped,keyPressed or keyRealeased
     */
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

    /**
     * Create a score and money textfield that the user can see
     */
    public void printScore(){
        String currentScore;
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

    /**
     * Updates the score for the user
     */
    public void updateScore(){
        score.setText("Score:"+String.valueOf(env.getScore()));
        money.setText("Money"+String.valueOf(env.getMoney()));

    }

    /**
     * Highscore table
     */
    public void highScoreTable(){
        try {
            JFrame scoreFrame = new JFrame();
            JPanel topPanel = new JPanel();

            Object data[][] = {{"ralle", "100000"},
                    {"ralle2", "2000"}};
            Object columnNames[] = {"Player", "Score"};
            scoreTable = new JTable();
            ArrayList<DBModel> dbHighScore = env.getHighScores();


            for (int i = 0; i < dbHighScore.size(); i++) {

                System.out.println(dbHighScore.get(i).toString());
            }

            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (getRowCount() > 0) {
                        return getValueAt(0, columnIndex).getClass();

                    }
                    return super.getColumnClass(columnIndex);
                }
            };

            for (int row = 0; row < dbHighScore.size(); row++) {
                Object[] rowData = {"Name: " + dbHighScore.get(row).getPlayername(), "Score: " + dbHighScore.get(row).getScore()};
                model.addRow(rowData);


            }
            JTextPane textPane = new JTextPane();
            textPane.setBackground(Color.black);
            this.appendToPane(textPane, "Player highscore", Color.white, 34);
            topPanel.add(textPane, BorderLayout.CENTER);

            scoreTable.setModel(model);
            scoreFrame.setSize(1000, 1000);
            scoreFrame.add(topPanel, BorderLayout.NORTH);
            scoreFrame.add(scoreTable, BorderLayout.CENTER);
            scoreFrame.setVisible(true);

        }catch (NoDatabaseConnectionException e){
            JOptionPane.showMessageDialog(null,"Game running in offline mode, nothing is saved to the database.\n" +
                    "To fix this:\n" +
                    "Make sure you are only running one instance of the game and restart.\n");
        }
    }

    /**
     *
     * @param tp
     * @param msg
     * @param c
     * @param fontSize
     */
    private void appendToPane(JTextPane tp, String msg, Color c, int fontSize)
    {
        Font f = new Font(Font.SANS_SERIF, 3 ,5);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Verdana");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        aset = sc.addAttribute(aset, StyleConstants.FontSize, fontSize);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
    public ArrayList getTowers(){
        return env.getTowers();
    }
    public void pauseTroopSound(){
        env.setPaused(true);
    }
    public void resumeTroopSound(){
        env.setPaused(false);
    }
}

