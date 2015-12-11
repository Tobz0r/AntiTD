package AntiTD;

import AntiTD.tiles.CrossroadSwitch;
import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.towers.BasicTower;
import AntiTD.towers.FrostTower;
import AntiTD.towers.Tower;
import AntiTD.troops.Troop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author dv13tes
 */
public class Environment extends JPanel implements Runnable,Observer {

    private int victoryScore;
    private final int minimumCredits=174;
    private int finalScore=0;
    private int credits;
    private int mapNr=0;
    private int restartMoney;

    private ArrayList<Tile> buildableTiles = new ArrayList<Tile>();
    private ArrayList<CrossroadSwitch> switches;
    private ArrayList<Level> levels;

    private Handler handler;

    private BufferedImage basicTower;
    private BufferedImage basicImage;

    private BufferedImage frostTower;
    private BufferedImage frostImage;

    private BufferedImage arrows;
    private BufferedImage arrowsImage;
    private Tile[][] map;

    private GUI gui;


    private  Executor runner= Executors.newFixedThreadPool(4);;

    private static boolean gameRunning;
    private static  boolean paused;
    private boolean gameOver;

    private Thread thread;

    private Level level;
    private Sounds sounds = new Sounds();


    public Environment(GUI gui, File fp){
        super(new BorderLayout());
        thread=null;
        this.gui=gui;
        gameOver=false;
        handler=new Handler(0,this);
        ReadXML xmlReader = new ReadXML(fp);
        levels=xmlReader.getLevels();
        level=levels.get(mapNr);
        map=level.getMap();
        setUpNeighbors();
        credits= 1000000;//level.getStartingCredits();
        Level.setCurrentMap(map);
        victoryScore=level.getVictoryPoints();
        try {
            basicTower= ImageIO.read(new File("sprites/basic.png"));
            frostTower= ImageIO.read(new File("sprites/frost.gif"));
            arrows= ImageIO.read(new File("sprites/arrowb.gif"));
            switches=level.setUpCrossroad();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initTowers();
        restartMoney=credits;

        for(CrossroadSwitch cSwitch:switches){
            addMouseListener(cSwitch);
        }
        setLayout(new GridLayout(1, 1));
        setPreferredSize(new Dimension(map.length * 70, map[0].length * 70));

    }

    private void setUpNeighbors() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                ArrayList<Tile> neighbors = new ArrayList<Tile>(8);
                for (int row = -1; row <= 1; row++) {
                    for (int col = -1; col <= 1; col++) {
                        if ( row+col == -1 || row+col == 1 ) {
                            if((((y+row)<map.length) && (0<=(y+row))) &&
                                    ((x+col>=0)&&((x+col)<map[0].length))) {
                                if (map[y + row][x + col].isMoveable()) {
                                    neighbors.add(map[y + row][x + col]);
                                }
                            }
                        }
                    }
                }
                Tile[] neighborsArr = new Tile[neighbors.size()];
                for (int index = 0; index < neighbors.size(); index++) {
                    neighborsArr[index] = neighbors.get(index);
                }
                map[y][x].setNeighbors(neighborsArr);
            }
        }
    }

    Level getLevel(){
        return level;
    }
    public synchronized void start(){
        paused=false;
        gameRunning=true;
        if (thread==null) {
            thread = new Thread(this);
            thread.start();
        }
    }
    public synchronized void stop(){
        try{
            gameRunning=false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e ){

        }
    }

    public static  boolean isPaused(){
        return paused;
    }
    public void startGame(){
        gameRunning=true;
    }
    public boolean isGameOver(){
        return gameOver;
    }


    public void paintComponent( Graphics g){
        setTileSize();
        g.clearRect(0, 0, getWidth(), getHeight());
       for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j].landOn(g);
            }
        }
        handler.render(g);

    }
    private void setTileSize(){
        Tile[][] map=Level.getCurrentMap();
        for(int i=0; i < map.length;i++){
            for(int j=0; j < map[i].length;j++){
                map[i][j].setSize(new Dimension(getWidth() / map.length, getHeight() / map[i].length));
            }
        }
    }
    public void run() {
        long lastTime = System.currentTimeMillis();
        double amountOfTicksPerSecond = 80;
        long ns = Math.round(1000.0 / amountOfTicksPerSecond);
        double delta = 0;
        int ticks=0;
        while(gameRunning){
            long now = System.currentTimeMillis();
            long wait = ns - (now - lastTime);
            lastTime = now;
            wait = wait < 0 ? 0 : wait;
            finishedLevel(wait);
            try {
                thread.sleep(wait);
                if (! isPaused()) {
                    finalScore++;
                    gui.updateScore();
                    runner.execute(new Runnable() {
                        public void run() {
                            handler.tick();
                        }
                    });
                    repaint();
                    ticks++;
                    if(ticks>60){
                        int value=handler.getAliveTroops().size();
                        credits+=(value*2);
                        ticks=0;
                    }


                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean isRunning(){
        return gameRunning;
    }

    public void addTroop(Troop troop){
        handler.addObject(troop);
    }
    public void addTower(Tower tower){
        handler.addObject(tower);
    }
 /*   public void addBullets(Bullets bullets){
        handler.addObject(bullets);
    }*/

    public ArrayList<Troop> getTroops(){
        return handler.getAliveTroops();
    }
    public static void pauseGame(){
        paused=true;
    }
    public static void resumeGame(){
        paused=false;
    }
    private void incrementLevel(boolean restart, boolean gameOver){
        pauseGame();
        int currentMap=mapNr;
        mapNr++;
        if(mapNr>levels.size()-1 || restart){
            restart = gameOver ? false : true;
            int reply = restart ? JOptionPane.YES_OPTION : JOptionPane.showConfirmDialog(null, "GG! \n Would you like to play again?",
                    "GG EZ!", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                sounds.pauseMusic();
                gui.resumeMainSound();
                mapNr=restart ? currentMap : 0;
                handler.resetScore();
                resetTeleport();
                restart=true;
            }
            else {
                JOptionPane.showMessageDialog(null, "GOODBYE");
                System.exit(0);
            }
        }
        for(CrossroadSwitch switc: switches){
            removeMouseListener(switc);
        }
        level=levels.get(mapNr);
        victoryScore=(level.getVictoryPoints()+handler.getVictoryScore());
        map=level.getMap();
        Level.setCurrentMap(map);
        credits+=level.getStartingCredits();
        restartMoney=credits;
        credits= restart ? level.getStartingCredits() : restartMoney;
        setUpNeighbors();
        Troop.clearTeleports();
        ArrayList<CrossroadSwitch>switches=level.setUpCrossroad();
        for(CrossroadSwitch cSwitch:switches){
            addMouseListener(cSwitch);
        }
        initTowers();
        resumeGame();
        gameRunning=true;

    }

    public void restartLevel(boolean restart){
        handler.resetGame();
        incrementLevel(restart,false);
    }

    private void finishedLevel(long wait){
        if(handler.getVictoryScore() >= victoryScore){
            handler.resetGame();
            if((mapNr+1)>levels.size()-1) {
                sounds.music("music/gameover.wav",false);
                gui.pauseMainSound();
                int reply = JOptionPane.showConfirmDialog(null, "GG! \n Would you like to play again?",
                        "GG EZ!", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    sounds.pauseMusic();
                    gui.resumeMainSound();
                    mapNr = -1;
                    incrementLevel(false, false);

                } else {
                    System.exit(0);
                }
            }
            else {
                incrementLevel(false, false);
            }
        }
        else if(!handler.hasAliveTroops() && (credits < minimumCredits)){
            gui.pauseMainSound();
            sounds.music("music/gameover.wav",false);
            gameRunning=false;
            incrementLevel(true, true);
        }
    }


    public int getScore() {
        return handler.getVictoryScore();
    }
    public int getMoney(){
        return credits;
    }
    public boolean buyUnit(int amount){
        if((credits-amount)>0) {
            credits -= amount;
            return true;
        }
        return false;
    }
    private void initTowers(){
        Tile[][] currentMap = Level.getCurrentMap();
        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                if (currentMap[i][j].isBuildable()) {
                  //  Bullets bullet = new Bullets(arrows   ,1,5,currentMap[i][j]);
                 //   addBullets(bullet);
                    addTower(new FrostTower(frostTower, currentMap[i][j], getTroops(),handler));
                    break;
                }
            }
        }
    }
    private void resetTeleport(){
        for(int i=0; i < levels.size(); i++){
            Tile[][] map=levels.get(i).getMap();
            for(int j=0; j < map.length; j++){
                for(int k=0; k < map[0].length;k++){
                    map[j][k].resetTeleport();
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        credits+=(int)arg;
    }
}
