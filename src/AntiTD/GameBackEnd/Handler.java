package AntiTD.GameBackEnd;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class Handler extends Thread {
    private LinkedList<GameObject> objects;
    private final static Object lockObject=new Object();
    final static Object notifier=new Object();
    private int tid;
    private Thread thread;

    private static Environment env;



    public Handler(int tid){
        this.tid=tid;
        objects=new LinkedList<>();
    }

    public LinkedList<GameObject> getGameObjects() {
        return objects;
    }

    public synchronized void  clearList(){
        for (int i = 0; i < objects.size() ; i++) {
            objects.remove(i);
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
            g.setColor(Color.blue);
            int x = gameObject.getTilePosition().getPosition().getX();
            int y = gameObject.getTilePosition().getPosition().getY();
            g.fillRect((int)x, (int)y, 24, 24);
        }
    }

}
