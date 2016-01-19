package AntiTD;

import AntiTD.towers.Tower;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Thom Renström
 * Menu class is designed to create a menu for the game
 * Each menu item on the menu have an actionlistener
 * Every actionlistener preformes an action depending on its task
 * There is two menubar one for accual game interference
 * the other one is more about information around the game.
 */
public class Menu extends JMenu {
    //startmenu
    private JMenuItem newGame,mainMenu, exitGame, pauseGame, mute, highScore;
    private JMenuBar startMenuBar = new JMenuBar();
    private JFrame frame;
    private GUI gui;

    private ArrayList<Tower> towerList;
    private boolean pauseMusic = false;
    private boolean pause = true;
    private boolean mutesound = true;
    private boolean mainMusic = false;
    private Environment env;
    //helpframe
    private JTextArea helpText;
    private JFrame helpFrame = new JFrame();
    private JScrollPane helpScroll;
    private JButton helpButton;
    private JPanel helpPanel;
    private JTable priceTable;
    //sound
    private Sounds sounds = new Sounds();

    //statmenu
    private JMenu statmenu = new JMenu("Help");
    private JMenuItem  nameChange, about, help;
    private JMenuBar statMenuBar = new JMenuBar();





    public Menu(JFrame frame, GUI gui, Environment env) {
        super("Start");
        this.env = env;
        this.gui = gui;
        this.frame = frame;
    }

    void updateEnvironment(Environment env){
        this.env=env;
    }

    /*
     * Take a string, and sets newGame button to that string
     */
    public void setNewGame(String change) {
        newGame.setText(change);
    }

    /**
     * Create the start menu with menu items
     * Each menu item have its own actionlistener
     * newGame restart current level or start a new game
     * pauseGame pauses the game or resumes
     * mute mutes the game or unmute
     * highScore shows highscorelist
     * mainMenu takes user back to main menu
     * exitGame quits the game
     */
    public void startMenu(){
        frame.setJMenuBar(startMenuBar);
        startMenuBar.add(this);
        //add items to menu
        newGame = this.add("Restart");
        newGame.setBackground(Color.white);
        if(!env.isRunning()){
            newGame.setText("New Game");
        }
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (env.isRunning()) {
                    gui.restartGame();
                    newGame.setText("Restart");
                    if(!mutesound){
                        gui.pauseMainSound();
                    }
                    else{
                        gui.resumeMainSound();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please Enter Name");
                }

            }

        });
        pauseGame = this.add("Pause");
        pauseGame.setBackground(Color.white);
        pauseGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(pause){
                    env.pauseGame();
                    pauseGame.setText("Resume");
                    gui.pauseMainSound();
                    pause=false;
                }
                else{
                    env.resumeGame();
                    pauseGame.setText("Pause");
                    gui.resumeMainSound();
                    pause=true;
                }
            }
        });
        mute = this.add("Mute");
        mute.setBackground(Color.white);
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                towerList = gui.getTowers();
                if(mutesound){
                    mainMusic=false;
                    pauseMusic = true;
                    gui.pauseMainSound();
                    env.pauseEnvSound();
                    if(env.isRunning()){
                        for(int i=0; i < towerList.size(); i++){
                            towerList.get(i).pauseTowerSound();
                        }
                        gui.pauseTroopSound();
                    }
                    mute.setText("Unmute");
                    mutesound=false;
                }
                else {
                    pauseMusic = false;
                    env.resumeEnvSound();
                    if(env.isRunning()){
                        gui.resumeMainSound();
                        for(int i=0; i < towerList.size(); i++){
                            towerList.get(i).resumeTowerSound();
                        }
                        gui.resumeTroopSound();
                    }
                    if(mainMusic){
                        gui.playMusic();
                    }
                    mute.setText("Mute");
                    mutesound = true;
                }
            }
        });

        highScore = this.add("High Score");
        highScore.setBackground(Color.white);
        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.highScoreTable();
            }
        });
        mainMenu = this.add("Main Menu");
        mainMenu.setBackground(Color.white);
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainMusic = true;
                gui.pauseMainSound();
                newGame.setText("New Game");
                gui.startScreen();
            }
        });
        exitGame = this.add("Quit");
        exitGame.setBackground(Color.white);
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        startMenuBar.add(this);
    }

    /**
     * @return true if its pause false if not
     */
    public boolean musicStatus(){
        return pauseMusic;
    }

    /**
     * Adds a menu bar to the menu, with menuitem help, about and namechange
     * Each menu item have its own actionlistener
     * help shows a new frame with info about how the game
     * about show info about who created the game
     * nameChange let the user change its name
     */
    public void statMenu(){
        frame.setJMenuBar(statMenuBar);
        statMenuBar.add(this);
        //lägga till menyitems
        help = statmenu.add("Help");
        help.setBackground(Color.white);
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                callHelpFrame();
            }
        });
        about = statmenu.add("About");
        about.setBackground(Color.white);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Game created by:\n Thom Renström \n " +
                        "Tobias Estefors \n Rasmus Dahlkvist \n Mattias Edin\n\n Assets by:\n " +
                        "David Gervais","About",1);

            }
        });
        nameChange = statmenu.add("Change name");
        nameChange.setBackground(Color.white);
        nameChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(env.isRunning()){
                    gui.changeName(JOptionPane.showInputDialog("Enter name"));
                }
                else{
                    JOptionPane.showMessageDialog(null,"Submit name first!");
                }
            }
        });
        statMenuBar.add(statmenu);
    }
    /**
     * Call the helpframe that will show when you press help on menu
     * resizes the images and add them to a table with all information
     * about the game.
     */
    private void callHelpFrame(){
        ArrayList<ImageIcon> icons = new ArrayList<>();
        //ogre
        BufferedImage ogre = null;
        try {
            ogre = ImageIO.read( this.getClass().getResourceAsStream("/sprites/ogre.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ogre= (BufferedImage) resizeImage(ogre,25,25);
        ImageIcon ogree= new ImageIcon(ogre);
        icons.add(ogree);

        //dragon
        BufferedImage dragon = null;
        try {
            dragon = ImageIO.read( this.getClass().getResourceAsStream("/sprites/redDragon.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dragon= (BufferedImage) resizeImage(dragon,25,25);
        ImageIcon dragonn= new ImageIcon(dragon);
        icons.add(dragonn);


        //earthElemental
        BufferedImage earth = null;
        try {
            earth = ImageIO.read( this.getClass().getResourceAsStream("/sprites/earthElemental.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        earth= (BufferedImage) resizeImage(earth,25,25);
        ImageIcon earthh= new ImageIcon(earth);
        icons.add(earthh);

        //teleporter
        BufferedImage tele = null;
        try {
            tele = ImageIO.read( this.getClass().getResourceAsStream("/sprites/Teleporter.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tele= (BufferedImage) resizeImage(tele,50,50);
        ImageIcon telee= new ImageIcon(tele);
        icons.add(telee);

        //priceTable
        String[] columns={"Units","Image"};
        Object[][] rows = {{ogre}};
        Object[][] data ={ {"Ogre",ogre},{"earth",earth},
        {"teleport",tele},{"dragon",dragon}
        };
        priceTable = new JTable();
        DefaultTableModel model = new DefaultTableModel(columns,0){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(getRowCount() > 0){
                    return getValueAt(0,columnIndex).getClass();
                }
                return super.getColumnClass(columnIndex);
            }
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String[] units = {"Ogre", "Dragon","EarthElement","Teleport"};
        ArrayList<JTextArea> textAreas = new ArrayList<>();
        JTextArea ogreText = new JTextArea("ogreText");
        ogreText.append("<html>Ogre <br> HP:200 \n Speed = 1</html>");
        textAreas.add(ogreText);
        JTextArea dragonText = new JTextArea("ogreText");
        dragonText.append("Ogre \n HP:200 \n Speed = 1");
        textAreas.add(dragonText);
        JTextArea earthText = new JTextArea("ogreText");
        earthText.append("Ogre \n HP:200 \n Speed = 1");
        textAreas.add(earthText);
        JTextArea teleText = new JTextArea("ogreText");
        teleText.append("Teleport \n HP:200 \n Speed = 1");
        textAreas.add(teleText);

        for(int row = 0; row <icons.size(); row++){
            Object[] rowData = {textAreas.get(row), icons.get(row)};
            model.addRow(rowData);
        }

        priceTable.setModel(model);
        priceTable.setRowHeight(240);
        priceTable.setBackground(Color.white);
        priceTable.setValueAt("<html><body style=background-color:lightgrey>" +
                " <font size= 6> Health: 5 <br> Speed: 5 <br>" +
                "Cost: $175" +
                "</font></body>" +
                "</html>", 0,0);
        priceTable.setValueAt("<html> <font size= 6> Health: 5 <br> Speed: 10 <br>" +
                "Cost: $325</font></html>", 1,0);
        priceTable.setValueAt("<html><font size= 6 style= Lucida Console>  Health: 100 <br> Speed: 1 <br>" +
                "Cost: $450 </font></html>", 2,0);
        priceTable.setValueAt("<html><font size= 6>  Health: 10 <br> Speed: 2 <br> Teleport distance = 3 <br>" +
                "Cost: $4000</font></html>", 3,0);

        helpPanel = new JPanel();
        helpPanel.setBackground(Color.black);
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
        JTextPane textPane = new JTextPane();
        textPane.setBackground(Color.black);
        this.appendToPane(textPane, "To Play start by choosing a name then you spawn troops with your " +
                "starting money. You will " +
                "get money if your unit reach goal", Color.white);
        JTextArea informationArea = new JTextArea();
        informationArea.setBackground(Color.black);
        informationArea.setSelectedTextColor(Color.white);
        helpPanel.add(textPane, BorderLayout.SOUTH);
        helpPanel.add(helpButton, BorderLayout.NORTH);

        helpFrame.setSize(1000, 1000);
        helpFrame.add(helpPanel, BorderLayout.SOUTH);
        helpFrame.add(priceTable, BorderLayout.CENTER);
        helpFrame.setVisible(true);
    }

    /**
     *
     * @param tp
     * @param msg
     * @param c
     */
    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Verdana");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    /**
     * Takes an image and resizes it
     * @param myImg the image you want to resize
     * @param w width of the image
     * @param h hight of the image
     * @return the resized image
     */
    private Image resizeImage(Image myImg, int w, int h){
        BufferedImage resizeImg = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizeImg.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(myImg,0,0,w,h,null);
        g.dispose();

        return resizeImg;
    }
}

