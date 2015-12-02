package AntiTD;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.RunnableFuture;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class Handler implements Runnable {
    private static LinkedList<GameObject> objects;
    private static Object lockObject=new Object();
    private static boolean update;
    private static Graphics g;

    public Handler(){
        objects=new LinkedList<>();
    }
    public static void addGraphics(Graphics g){
        Handler.g=g;
    }
    public static synchronized void addObject(GameObject object){
        objects.add(object);
    }
    public static synchronized void removeObject(GameObject object){
        objects.remove(object);
    }
    public  void tick(){

    }
    public static void toUpdate(boolean update){
        Handler.update=update;
    }
    public  void render(Graphics g){

    }


    @Override
    public void run() {
        while (true) {
            if (update) {
                for (int i = 0; i < objects.size(); i++) {
                    synchronized (lockObject) {
                        System.out.println(i + " tick ");
                        objects.get(i).tick();
                    }
                }
                for (int i = 0; i < objects.size(); i++) {
                    synchronized (lockObject) {
                        System.out.println(i + " render ");
                        objects.get(i).render(g);
                    }
                }
            }
            notifyAll();
        }
    }
}
