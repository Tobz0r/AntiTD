package AntiTD;

import AntiTD.towers.Tower;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
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
import java.util.ArrayList;

/**
 * @author dv13trm
 */
public class Menu extends JMenu {
    //startmenu
    private JMenuItem newGame,mainMenu, exitGame, pauseGame, mute, highScore;
    private JMenuBar startMenuBar = new JMenuBar();
    private JFrame frame;
    private GUI gui;

    private ArrayList<Tower> towerList;
    private boolean pauseMusic;
    private boolean pause = true;
    private boolean mutesound = true;
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
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please Enter Name");
                }

            }

        });
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.pauseMainSound();
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
                    gui.pauseMainSound();
                    pause=false;
                }
                else{
                    Environment.resumeGame();
                    pauseGame.setText("Pause");
                    gui.resumeMainSound();
                    pause=true;
                }
            }
        });
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                towerList = gui.getTowers();
                if(mutesound){
                    pauseMusic = true;
                    gui.pauseMainSound();
                    env.pauseEnvSound();
                    if(Environment.isRunning()){
                        for(int i=0; i < towerList.size(); i++){
                            towerList.get(i).pauseTowerSound();
                        }
                    }
                    if(sounds.isPlaying()){
                        sounds.pauseMusic();
                    }


                    mute.setText("Unmute");
                    mutesound=false;
                }
                else {
                    pauseMusic = false;
                    if(Environment.isRunning()){
                        gui.resumeMainSound();

                    }
                    else{
                        sounds.music("music/start.wav", true);
                    }


                    env.resumeEnvSound();
                    if(Environment.isRunning()){
                        for(int i=0; i < towerList.size(); i++){
                            towerList.get(i).resumeTowerSound();
                        }
                    }

                    mute.setText("Mute");
                    mutesound = true;
                }
            }
        });


        startMenuBar.add(this);
    }
    public boolean musicStatus(){
        return pauseMusic;
    }

    public void statMenu(){
        frame.setJMenuBar(statMenuBar);
        statMenuBar.add(this);
        //lägga till menyitems
        help = statmenu.add("Help");
        about = statmenu.add("About");


        nameChange = statmenu.add("Change name");
        nameChange.setBackground(Color.white);

        help.setBackground(Color.white);
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
                if(Environment.isRunning()){
                    gui.changeName(JOptionPane.showInputDialog("Enter name"));
                }
                else{
                    JOptionPane.showMessageDialog(null,"Submit name first!");
                }
            }
        });

        statMenuBar.add(statmenu);
    }

    private void callHelpFrame(){
        ArrayList<ImageIcon> icons = new ArrayList<>();
        //ogre
        BufferedImage ogre = null;
        try {
            ogre = ImageIO.read(new File("sprites/ogre.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ogre= (BufferedImage) resizeImage(ogre,25,25);
        ImageIcon ogree= new ImageIcon(ogre);
        icons.add(ogree);


        //dragon
        BufferedImage dragon = null;
        try {
            dragon = ImageIO.read(new File("sprites/redDragon.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dragon= (BufferedImage) resizeImage(dragon,25,25);
        ImageIcon dragonn= new ImageIcon(dragon);
        icons.add(dragonn);


        //earthElemental
        BufferedImage earth = null;
        try {
            earth = ImageIO.read(new File("sprites/earthElemental.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        earth= (BufferedImage) resizeImage(earth,25,25);
        ImageIcon earthh= new ImageIcon(earth);
        icons.add(earthh);

        //teleporter
        BufferedImage tele = null;
        try {
            tele = ImageIO.read(new File("sprites/Teleporter.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tele= (BufferedImage) resizeImage(tele,50,50);
        ImageIcon telee= new ImageIcon(tele);
        icons.add(telee);


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
        Object[][] data ={ {"Ogre",ogre},{"earth",earth},
        {"teleport",tele},{"dragon",dragon}
        };

        priceTable = new JTable();
       // priceTable.setValueAt(ogree, 1,1);
        JTable unitTable = new JTable();

        DefaultTableModel model = new DefaultTableModel(columns,0){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(getRowCount() > 0){
                    return getValueAt(0,columnIndex).getClass();

                }
                return super.getColumnClass(columnIndex);
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



        int y1 = 20;
        for(int row = 0; row <icons.size(); row++){
            Object[] rowData = {textAreas.get(row), icons.get(row)};
            model.addRow(rowData);
            //model.addColumn(units);
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
        /*unitTable.setModel(model);
        unitTable.setRowHeight(((ImageIcon)model.getValueAt(0,1)).getIconHeight());*/









        helpPanel = new JPanel();
        helpPanel.setBackground(Color.black);
       // helpPanel.add(unitTable.getTableHeader());;
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
        /*informationArea.setText("To Play start by choosing a name then you spawn troops with your " +
                "starting money. You will " +
                "get money if your unit reach goal");*/
        informationArea.setBackground(Color.black);
        informationArea.setSelectedTextColor(Color.white);
        helpPanel.add(textPane, BorderLayout.SOUTH);
        helpPanel.add(helpButton, BorderLayout.NORTH);

        helpFrame.setSize(1000, 1000);
        /*helpFrame.add(helpText);
        helpScroll = new JScrollPane(helpText);
        helpFrame.add(helpScroll, BorderLayout.CENTER);*/

       // helpFrame.getContentPane().setBackground(Color.yellow);
        helpFrame.add(helpPanel, BorderLayout.SOUTH);
        helpFrame.add(priceTable, BorderLayout.CENTER);
        helpFrame.setVisible(true);
    }
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

    private Image resizeImage(Image myImg, int w, int h){
        BufferedImage resizeImg = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizeImg.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(myImg,0,0,w,h,null);
        g.dispose();

        return resizeImg;
    }



}

