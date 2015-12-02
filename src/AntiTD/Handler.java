package AntiTD;

import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.Semaphore;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class Handler implements Runnable {
    private static LinkedList<GameObject> objects;
    private static Object lockObject=new Object();
    private static Object mutexeliashej=new Object();
    private static Graphics g;

    public Handler(){
        objects=new LinkedList<>();
    }
    public static void addGraphics(Graphics g){
        Handler.g=g;
    }
    public static synchronized void  clearList(){
        for (int i = 0; i < objects.size() ; i++) {
            objects.remove(i);
        }
    }
    public static synchronized void addObject(GameObject object){
        objects.add(object);
    }
    public static synchronized void removeObject(GameObject object){
        objects.remove(object);
    }
    public  void tick(){
        for (int i = 0; i < objects.size(); i++) {
            synchronized (lockObject) {
                objects.get(i).tick();
            }
        }

    }
    public  void render(Graphics g){
        for (int i = 0; i < objects.size(); i++) {
            synchronized (lockObject) {
                objects.get(i).render(g);
            }
        }

    }


    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        final int nrOfTicks = 60;
        final long OPTIMAL_TIME = 1000000000 / nrOfTicks;
        while (Environment.isRunning()){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);
            if(!Environment.isPaused()) {
                for (int i = 0; i < objects.size(); i++) {
                    synchronized (lockObject) {
                        System.out.println(" tick " );
                        objects.get(i).tick();
                    }
                    synchronized (lockObject) {
                        System.out.println(" render " );
                        objects.get(i).render(g);
                    }
                }
            }
            try {
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
