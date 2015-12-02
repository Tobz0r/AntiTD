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
public class Environment extends JPanel {

    private ArrayList<Level> levels;
    private Handler handler;
    private Handler handler2;

    private static boolean gameRunning;
    private static boolean paused;

    private Tile[][] map;
    private Thread[] threads;

    private int mapNr=1;
    private final int nrthr=2;
    private Level level;

    private Object lock=new Object();

    public Environment(){
        super(new BorderLayout());
        handler=new Handler();
        handler2=new Handler();
        threads=new Thread[nrthr];
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();
        Level level=levels.get(mapNr);
        map=level.getMap();
        setLayout(new GridLayout(1,1));
        setPreferredSize(new Dimension(map.length*48,map[0].length*48));

    }

    public void start(){
        paused=false;
        threads[0]=new Thread(handler);
        threads[1]=new Thread(handler2);
        threads[0].start();
        threads[1].start();
    }
    public static boolean isRunning(){
        return gameRunning;
    }
    public static boolean isPaused(){
        return paused;
    }
    public void startGame(){
        gameRunning=true;
    }

    public  void clearBoard(){
        repaint();
    }

    public void paintComponent(Graphics g){
        Handler.addGraphics(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j].landOn(g);
            }
        }
        handler.render(g);
    }
    public void run() {

    }
    public void addTroops(Troop troop){
        Handler.addObject(troop);
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
