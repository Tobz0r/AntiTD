package AntiTD;

import AntiTD.tiles.Tile;
import AntiTD.towers.Tower;
import AntiTD.troops.Troop;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.Semaphore;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class Handler {
    private LinkedList<GameObject> objects;
    private int tid;
    private int score;
    //private Thread thread;
    //private ArrayList<Troop> troops = new ArrayList();
    private LinkedList<GameObject> aliveTroops;
    private LinkedList<GameObject> towers;


    public Handler(int tid) {
        this.tid = tid;
        objects = new LinkedList<GameObject>();
        aliveTroops = new LinkedList<GameObject>();
        towers = new LinkedList<GameObject>();

        //thread = new Thread(this);
        //thread.start();
        score = 0;
    }

    public boolean hasAliveTroops() {
        for (GameObject temp : objects) {
            if (temp instanceof Troop) {
                if (((Troop) temp).isAlive()) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void clearList() {
        objects.clear();
        towers.clear();
        aliveTroops.clear();
        /*
        int i = objects.size() - 1;
        while (objects.size() != 0) {
            objects.remove(i);
            i--;
        }
        */
    }

    public synchronized void addObject(GameObject object) {
        objects.add(object);
        if (object instanceof Troop) {
            aliveTroops.add(object);
            for (GameObject go : towers) {
                Tower t = (Tower) go;
                t.addTroopToList((Troop)object);
            }
        } else if (object instanceof Tower) {
            towers.add(object);
        }
    }


    public synchronized void removeObject(GameObject object) {
        objects.remove(object);
        if (object instanceof Troop) {
            aliveTroops.remove(object);
            for (GameObject go : towers) {
                Tower t = (Tower) go;
                t.removeTroopFromList((Troop)object);
            }
        } else if (object instanceof Tower) {
            towers.remove(object);
        }
    }


    public ArrayList<Troop> getAliveTroops() {
        ArrayList<Troop> list = new ArrayList<Troop>(aliveTroops.size());
        for (GameObject go : aliveTroops) {
            list.add((Troop) go);
        }
        return list;
    }
    public void addTroop(Troop troop) {
        objects.add(troop);
        aliveTroops.add(troop);
        for (GameObject go : towers) {
            Tower t = (Tower) go;
            t.addTroopToList(troop);

        }
        /*
        int j = 0;

        this.troops = troops;
        for (int i = 0; i < objects.size(); i++) {
            GameObject gameObject = objects.get(i);
            if (gameObject.type().equals("Tower")) {
                j++;
                System.out.println("inserting troops: " + j);
                ((Tower) gameObject).setTroopsToList(troops);
            }
        }
        */
    }



    public synchronized void tick() {
        LinkedList<GameObject> gameObjectsToRemove = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            try {
                GameObject gameObject = objects.get(i);
                objects.get(i).tick();
                if (gameObject.type().equals("Troop")) {

                    score += gameObject.getCurrentScore();

                    Troop t = (Troop) gameObject;
                    if (!t.isAlive()) {
                        gameObjectsToRemove.add(objects.get(i));
                    }

                } else if (gameObject.type().equals("Tower")) {
                    //objects.get(i).tick();
                    /*if (!troops.isEmpty()) {
                        objects.get(i).tick();
                    }*/
                }

            } catch (java.util.ConcurrentModificationException e) {
                Throwable cause = e.getCause();
                // e.printStackTrace();
                System.out.println(cause.getMessage());
            }
        }
        for (GameObject go : gameObjectsToRemove) {
            removeObject(go);
            //objects.remove(go);
        }
        //System.out.println("Score: "+score);
    }

    public void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            try {
                GameObject gameObject = objects.get(i);
                boolean shouldDraw = true;
                if (gameObject instanceof Troop) {
                    Troop troop = (Troop) gameObject;
                    if (!troop.isAlive() || troop.hasReacedGoal()) {
                        shouldDraw = false;
                    }
                }
                if (shouldDraw) {
                    g.setColor(Color.blue);
                    int sizeX = (int) gameObject.getTilePosition().getSize().getWidth();
                    int sizeY = (int) gameObject.getTilePosition().getSize().getHeight();

                    Position position = gameObject.getTilePosition().getPosition();
                    double x_start = (position.getX() * sizeX) * 1.0;
                    double y_start = (position.getY() * sizeY) * 1.0;

                    Tile moveTo = gameObject.getMoveToPosition();
                    double x_to = (moveTo.getPosition().getX() * sizeX) * 1.0;
                    double y_to = (moveTo.getPosition().getY() * sizeY) * 1.0;

                    Double progress = (gameObject.getMoveProgres() * 1.0) / 100.0;
                    double x_global = x_start - x_to;
                    double y_global = y_start - y_to;

                    Long x_current = Math.round(x_start - (x_global * progress.doubleValue()));
                    Long y_current = Math.round(y_start - (y_global * progress.doubleValue()));
                    int troopSizeX = (int) gameObject.getTilePosition().getSize().getWidth() / 3;
                    int troopSizeY = (int) gameObject.getTilePosition().getSize().getHeight() / 3;
                    //int x = Math.round(position.getX()*size+(size*progress));
                    //int y = Math.round(position.getY()*size+(size*progress));
                    g.fillRect(x_current.intValue(), y_current.intValue(), troopSizeX, troopSizeY);
                }

            } catch (NullPointerException e) {
                System.out.println("plz slow down..");
            }
        }
    }

    public void reset() {
        objects.clear();
        aliveTroops.clear();
        towers.clear();
        score = 0;
    }

    public int getVictoryScore() {
        return score;
    }
}
