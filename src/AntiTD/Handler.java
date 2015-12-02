package AntiTD;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.RunnableFuture;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class Handler extends Thread {
    private static LinkedList<GameObject> objects;

    public Handler(){
        objects=new LinkedList<>();
    }
    public static synchronized void addObject(GameObject object){
        objects.add(object);
    }
    public static synchronized void removeObject(GameObject object){
        objects.remove(object);
    }
    public synchronized void tick(){
        for(int i=0; i < objects.size(); i++){
            objects.get(i).tick();
        }
    }
    public synchronized void render(Graphics g){
        for(int i=0; i < objects.size(); i++){
            objects.get(i).render(g);
        }
    }


}
