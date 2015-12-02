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
    private Handler handler;
    private boolean gameRunning;
    private boolean isPaused;
    private Tile[][] map;
    private Thread thread;
    private int mapNr=0;
    private Level level;

    public Environment(){
        super(new BorderLayout());
        handler=new Handler();
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();
        Level level=levels.get(mapNr);
        map=level.getMap();
        setLayout(new GridLayout(1,1));
        setPreferredSize(new Dimension(map.length*48,map[0].length*48));
    }

    public synchronized void start(){
        thread=new Thread(this);
        thread.start();
        isPaused=false;
    }
    public void startGame(){
        gameRunning=true;
    }

    @Override
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
                System.out.println("elias");
                handler.tick();
                repaint();
            }
            try {
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void paintComponent(Graphics g){
        if(gameRunning) {
            g.clearRect(0, 0, getWidth(), getHeight());
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    map[i][j].landOn(g);
                }
            }
            handler.render(g);
        }
        else{
            paintStart(g);
        }
    }
    private void paintStart(Graphics g){
        g.setFont(new Font("TimesRoman", Font.BOLD,24));
        g.drawString("Welcome to AntiTD",getWidth()/4,getHeight()/2);
    }
    public void addTroops(Troop troop){
        handler.addObject(troop);
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
