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

/**
 * @author dv13tes
 */
public class Environment extends JPanel implements Runnable {

    private ArrayList<Level> levels;
    private Handler tickHandler;
    private Handler renderHandler;

    private boolean gameRunning;
    private boolean isPaused;

    private Tile[][] map;
    private Thread[] threads;
    private Thread mainThread;

    private int mapNr=0;
    private final int nrthr=2;
    private Level level;
    private Graphics g;

    private Object lock=new Object();

    public Environment(){
        super(new BorderLayout());
        renderHandler=new Handler();
        tickHandler=new Handler();
        threads=new Thread[nrthr];
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();
        Level level=levels.get(mapNr);
        map=level.getMap();
        setLayout(new GridLayout(1,1));
        setPreferredSize(new Dimension(map.length*48,map[0].length*48));
        threads[0]=new Thread(tickHandler);
        threads[1]=new Thread(renderHandler);
        threads[0].start();
        threads[1].start();
    }

    public synchronized void start(){
        mainThread=new Thread(this);
        mainThread.start();
        isPaused=false;
    }
    public void startGame(){
        gameRunning=true;
    }


    public void paintComponent(Graphics g){
        this.g=g;
        Handler.addGraphics(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j].landOn(g);
            }
        }
    }
    public void run() {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        while (gameRunning){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);
            if(!isPaused) {
                repaint();
                Handler.toUpdate(true);
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Handler.toUpdate(false);
            try {
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void addTroops(Troop troop){
        Handler.addObject(troop);
    }
    public void pauseGame(){
        isPaused=true;
    }
    public void resumeGame(){
        isPaused=false;
    }
    private void incrementLevel(){
        mapNr++;
        if(mapNr>levels.size()-1){
            mapNr=0;
        }
    }
}
