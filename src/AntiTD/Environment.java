package AntiTD;

import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author dv13tes
 */
public class Environment extends JPanel implements Runnable {

    private ArrayList<Level> levels;
    private Handler handler;
    private Handler handler2;
    private  Executor runner= Executors.newFixedThreadPool(2);;


    private static boolean gameRunning;
    private static  boolean paused;

    private Tile[][] map;
    private Thread thread;
    private int mapNr=0;
    private final int nrthr=2;
    private Level level;

    private double delta;

    private Object lock=new Object();

    public Environment(){
        super(new BorderLayout());
        handler=new Handler(0);
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();
        level=levels.get(mapNr);
        map=level.getMap();
        setUpNeighbors();
        Level.setCurrentMap(map);
        level.setUpCrossroad();

        setLayout(new GridLayout(1, 1));
        setPreferredSize(new Dimension(map.length * 48, map[0].length * 48));

    }

    private void setUpNeighbors() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                ArrayList<Tile> neighbors = new ArrayList<Tile>(8);
                for (int row = -1; row <= 1; row++) {
                    for (int col = -1; col <= 1; col++) {
                        if ( ! ((row == 0) && (col == 0)) ) {
                            try {
                                neighbors.add(map[y-row][x-col]);
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



    public void paintComponent( Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
       for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j].landOn(g);
            }
        }
        handler.render(g);

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
            //System.out.println(wait);
            try {
                thread.sleep(wait);
                if (! isPaused()) {
                    System.out.println("tick");
                    handler.tick();
                    repaint();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            long now = System.currentTimeMillis();
//            //delta += (now - lastTime) / ns;
//            lastTime = now;
//            long wait = 1000 - lastTime;
//            try {
//                thread.sleep(wait < 0 ? 0 : wait);
//
//            } catch (InterruptedException e) {
//                //
//            }
//            if (!isPaused()) {
//                handler.tick();
//                /*
//                runner.execute(new Runnable() {
//                    public void run() {
//                        handler.tick();
//                    }
//                });
//                */
//                repaint();
//            }
//
//
//            /*
//            while(delta >= 1 && !isPaused()) {
//
//
//                });
//                delta--;
//            }
//            if(!isPaused()){
//
//                repaint();
//            }
//            */
        }
    }
    public static boolean isRunning(){
        return gameRunning;
    }
    public void addTroops(Troop troop){
        handler.addObject(troop);
    }
    public static void pauseGame(){
        paused=true;
    }
    public static void resumeGame(){
        paused=false;
    }
    private void incrementLevel(){
        mapNr++;
        if(mapNr>levels.size()-1){
            mapNr=0;
        }
    }
}
