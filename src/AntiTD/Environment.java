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

        Level.setCurrentMap(map);
        setLayout(new GridLayout(1, 1));
        setPreferredSize(new Dimension(map.length * 48, map[0].length * 48));

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
        long lastTime = System.nanoTime();
        double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int ticks=0;
        while(gameRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1 && !isPaused()) {

                runner.execute(new Runnable() {
                    public void run() {
                        handler.tick();
                    }
                });
                delta--;
            }
            if(!isPaused()){

                repaint();
            }

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
