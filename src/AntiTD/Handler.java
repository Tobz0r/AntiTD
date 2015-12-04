package AntiTD;

import AntiTD.tiles.Tile;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.Semaphore;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class Handler extends Thread {
    private static LinkedList<GameObject> objects;
    private final static Object lockObject=new Object();
    final static Object notifier=new Object();
    private int tid;
    private Thread thread;

    private static Environment env;



    public Handler(int tid){
        this.tid=tid;
        objects=new LinkedList<>();
    }


    public static synchronized void  clearList(){
        int i=objects.size()-1;
        while(objects.size()!=0){
            objects.remove(i);
            i--;
        }
    }
    public void addObject(GameObject object){
        objects.add(object);
    }
    public void removeObject(GameObject object){
        objects.remove(object);
    }

    public void tick(){
        for (int i = 0; i < objects.size(); i++) {
                objects.get(i).tick();
        }
    }
    public void render(Graphics g){
        for (int i = 0; i < objects.size(); i++) {
            //objects.get(i).render(g);
            GameObject gameObject = objects.get(i);
            if(gameObject.type().equals("Troop")) {
                System.out.println("I troops");
                g.setColor(Color.blue);
                int size = gameObject.getTilePosition().getSize();

                Tile position = gameObject.getTilePosition();
                Tile moveTo = gameObject.getMoveToPosition();
                float progress = gameObject.getMoveProgres() / 100;

                int x = Math.round(position.getPosition().getX() * size + (size * progress));
                int y = Math.round(position.getPosition().getY() * size + (size * progress));
                g.fillRect((int) x, (int) y, 24, 24);
            }
            if(gameObject.type().equals("Tower")){
                g.setColor(Color.red);
                int size = gameObject.getTilePosition().getSize();

                int x = gameObject.getPosition().getX()*size;
                int y = gameObject.getPosition().getY()*size;
                g.fillRect((int) x, (int) y, 24,24);
            }
        }
    }

}
