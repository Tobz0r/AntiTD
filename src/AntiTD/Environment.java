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
 * Created by mattias on 2015-11-27.
 */
public class Environment extends JComponent implements Runnable {

    private ArrayList<Level> levels;
    private Handler handler;
    private boolean gameRunning;
    private boolean isPaused;
    private Tile[][] map;
    private Thread thread;
    private int mapNr=1;
    private Level level;

    public Environment(){
        handler=new Handler();
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();
        Level level=levels.get(mapNr);
        map=level.getMap();
    }

    public synchronized void start(){
        thread=new Thread(this);
        gameRunning=true;
        isPaused=false;
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
        g.clearRect(0,0,map.length*48,map[0].length*48);
        int x,y;
        x=-48;
        for(int i=0; i < map.length;i++){
            x+=48;
            y=0;
            for(int j=0; j < map[i].length;j++){
                switch(map[i][j].toString()) {
                    case "AntiTD.tiles.BasicTile":
                        g.setColor(Color.black);
                        break;
                    case "AntiTD.tiles.CrossroadTile":
                        g.setColor(Color.red);
                        break;
                    case "AntiTD.tiles.GoalTile":
                        g.setColor(Color.yellow);
                        break;
                    case "AntiTD.tiles.PathTile":
                        g.setColor(Color.orange);
                        break;
                    case "AntiTD.tiles.TowerTile":
                        g.setColor(Color.green);
                        break;
                    case "AntiTD.tiles.StartTile":
                        g.setColor(Color.pink);
                        break;
                    default:
                        System.out.println("eliashej");
                        break;
                }
                g.fillRect(x,y,48,48);
                y+=48;
            }
        }
        handler.render(g);
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
        if(mapNr>levels.size()){
            mapNr=0;
        }
    }
}
