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
                g.setColor(Color.blue);
                int size = gameObject.getTilePosition().getSize();
    
                Position position = gameObject.getTilePosition().getPosition();
                double x_start = (position.getX()*size)*1.0;
                double y_start = (position.getY()*size)*1.0;
    
                Tile moveTo = gameObject.getMoveToPosition();
                double x_to = (moveTo.getPosition().getX()*size)*1.0;
                double y_to = (moveTo.getPosition().getY()*size)*1.0;
    
                Double progress = (gameObject.getMoveProgres()*1.0) / 100.0;
                double x_global = x_start - x_to;
                double y_global = y_start - y_to;
    
                Long x_current = Math.round(x_start - (x_global * progress.doubleValue()) );
                Long y_current = Math.round(y_start - (y_global * progress.doubleValue()) );
    
                //int x = Math.round(position.getX()*size+(size*progress));
                //int y = Math.round(position.getY()*size+(size*progress));
                g.fillRect(x_current.intValue(), y_current.intValue(), 24, 24);
        
            } else if(gameObject.type().equals("Tower")){
                g.setColor(Color.red);
                int size = gameObject.getTilePosition().getSize();

                int x = gameObject.getPosition().getX()*size;
                int y = gameObject.getPosition().getY()*size;
                g.fillRect((int) x, (int) y, 24,24);
            }
        }
    }
   

}
