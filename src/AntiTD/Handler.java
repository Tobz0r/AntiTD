package AntiTD;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class Handler {
    private LinkedList<GameObject> objects;

    public Handler(){

    }
    public void addObject(GameObject object){
        objects.add(object);
    }
    public void removeObject(GameObject object){
        objects.remove(object);
    }
    public void tick(){
        for(int i=0; i < objects.size(); i++){
            objects.get(i).tick();
        }
    }
    public void render(Graphics g){
        for(int i=0; i < objects.size(); i++){
            objects.get(i).render(g);
        }
    }

}
