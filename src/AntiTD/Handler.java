package AntiTD;

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
            objects.get(i).render(g);
        }
    }

}
