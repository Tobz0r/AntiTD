package AntiTD;

import AntiTD.tiles.CrossroadSwitch;
import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.towers.Tower;
import AntiTD.troops.Troop;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author dv13tes
 */
public class Environment extends JPanel implements Runnable {

    private ArrayList<Level> levels;
    private Handler handler;
    private int finalScore=10000000;
    private Handler handler2;
    private  Executor runner= Executors.newFixedThreadPool(2);;
    private ArrayList<Tile> buildableTiles = new ArrayList<Tile>();
    private ArrayList<Troop> troops = new ArrayList<>();
    private ArrayList<CrossroadSwitch> switches;

    private static boolean gameRunning;
    private static  boolean paused;

    private Tile[][] map;
    private Thread thread;
    private int mapNr=0;
    private final int nrthr=2;
    private Level level;

    private double delta;
    private boolean gameOver;
    private GUI gui;
    private Object lock=new Object();

    public Environment(GUI gui){
        super(new BorderLayout());
        this.gui=gui;
        gameOver=false;
        handler=new Handler(0);
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();
        level=levels.get(mapNr);
        map=level.getMap();
        setUpNeighbors();

        Level.setCurrentMap(map);
        switches=level.setUpCrossroad();
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
                            try {
                                if(map[y+row][x+col].isMoveable())
                                    neighbors.add(map[y+row][x+col]);
                            } catch (IndexOutOfBoundsException e) {

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
        thread=new Thread(this);
        thread.start();
    }
    public synchronized void stop(){
        try{
            gameRunning=false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e ){
            System.out.println("eliashej");

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
            for (int j = 0; j < map[i].length; j++) {
                map[i][j].landOn(g);
            }
        }
        handler.render(g);

    }
    private void setTileSize(){
        Tile[][] map=Level.getCurrentMap();
        for(int i=0; i < map.length;i++){
            for(int j=0; j < map[i].length;j++){
                map[i][j].setSize(new Dimension(getWidth()/map.length,getHeight()/map[i].length));
            }
        }
    }
    public void run() {
        long lastTime = System.currentTimeMillis();
        double amountOfTicksPerSecond = 60.0;
        long ns = Math.round(1000.0 / amountOfTicksPerSecond);
        double delta = 0;
        int ticks=0;

        while(gameRunning){
            long now = System.currentTimeMillis();
            long wait = ns - (now - lastTime);
            lastTime = now;
            wait = wait < 0 ? 0 : wait;
            finishedLevel(wait);
            //System.out.println(wait);
            try {
                thread.sleep(wait);
                if (! isPaused()) {
                    finalScore--;
                    gui.updateScore();
                    runner.execute(new Runnable() {
                        public void run() {
                            handler.tick();
                        }
                    });
                    repaint();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean isRunning(){
        return gameRunning;
    }
    public void addTroops(Troop troop){
        troops.add(troop);
        handler.addTroop(troops);
        handler.addObject(troop);
    }
    public void addTower(Tower tower){ Handler.addObject(tower);}
    public void saveBuildableTilese(){
        Tile pos;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j <map[i].length; j++){
                if(map[i][j].isBuildable()){
                    pos = map[i][j];
                    buildableTiles.add(pos);
                }

            }
        }
    }
    public ArrayList<Troop> getTroops(){
        return troops;
    }
    public Tile getBuildAbleTile(int i){
        return buildableTiles.get(i);
    }
    public static void pauseGame(){
        paused=true;
    }
    public static void resumeGame(){
        paused=false;
    }
    private void incrementLevel(){
        pauseGame();
        mapNr++;
        if(mapNr>levels.size()-1){
            int reply = JOptionPane.showConfirmDialog(null, "EZ GAEM, You're score : " + finalScore + "Would you laeik to play again?", "GG EZ!", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                mapNr=0;
            }
            else {
                JOptionPane.showMessageDialog(null, "GOODBYE");
                System.exit(0);
            }
        }
        for(CrossroadSwitch switc: switches){
            removeMouseListener(switc);
        }
        Handler.clearList();
        level=levels.get(mapNr);
        map=level.getMap();
        Level.setCurrentMap(level.getMap());
        Troop.resetScore();
        setUpNeighbors();
        ArrayList<CrossroadSwitch>switches=level.setUpCrossroad();
        for(CrossroadSwitch cSwitch:switches){
            addMouseListener(cSwitch);
        }
        resumeGame();
    }

    private void finishedLevel(long wait){
        if(Troop.getVictoryScore() >= level.getVictoryPoints()){
            incrementLevel();
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
