package AntiTD;

import AntiTD.tiles.Tile;
import AntiTD.towers.Projectile;
import AntiTD.towers.Tower;
import AntiTD.troops.Troop;

import java.awt.*;
import java.util.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class Handler extends Observable {
    private LinkedList<GameObject> objects;
    private int tid;
    private int aliveCount;
    private int score;
    private boolean resetFlag;
    private LinkedList<GameObject> aliveTroops;
    private LinkedList<GameObject> towers;
    private LinkedList<GameObject> objectsToAdd;
    private LinkedList<GameObject> objectsToRemove;



    public Handler(int tid, Environment env) {
        this.tid = tid;
        addObserver(env);
        objects = new LinkedList<>();
        aliveTroops = new LinkedList<>();
        towers = new LinkedList<>();
        objectsToAdd = new LinkedList<>();
        objectsToRemove = new LinkedList<>();
        score = 0;
        resetFlag = false;
        aliveCount=0;
    }

    public  boolean hasAliveTroops() {
        return aliveCount>0;
    }


    /**
     * Adds all objects to the game world that has been
     * added by <b>addObject()</b> method. <br /> <br />
     * <b>**Caution**</b> <br />
     * Should only be called in run method for thread safety
     */
    private synchronized void addObjectsToGame() {
        for (GameObject object : objectsToAdd) {
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
        objectsToAdd.clear();
    }

    /**
     * Add object to the game.
     * <br /> <br />
     * <br>**Note**</b><br />
     * The object will not be added until <b>tick()</b> method finishes
     * for thread safety reasons.
     * @param object object to add.
     */
    public synchronized void addObject(GameObject object) {
        if(object instanceof  Troop){
            aliveCount++;
        }
        objectsToAdd.add(object);
    }

    /**
     * Removes all objects from the game world that has been
     * added by <b>removeObject()</b> method. <br /> <br />
     * <b>**Caution**</b> <br />
     * Should only be called in <b>tick()</b> method for thread safety
     */
    private synchronized void removeObjectsFromGame() {

          for (GameObject object : objectsToRemove) {
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
        objectsToRemove.clear();
    }

    /**
     * Remove object from the game.
     * <br /> <br />
     * <b>**Note**</b><br />
     * The object will not be removed until <b>tick()</b> method finishes
     * for thread safety reasons.
     * @param object object to remove.
     */
    public synchronized void removeObject(GameObject object) {
        objectsToRemove.add(object);
    }


    public synchronized ArrayList<Troop> getAliveTroops() {
        ArrayList<Troop> list = new ArrayList<Troop>(aliveTroops.size());
        for (GameObject go : aliveTroops) {
            list.add((Troop) go);
        }
        return list;
    }


    public synchronized void tick() {
        for (int i = 0; i < objects.size(); i++) {
            try {
                GameObject gameObject = objects.get(i);
                objects.get(i).tick();
                if (gameObject instanceof Troop) {
                    score += gameObject.getCurrentScore();
                    Troop t = (Troop) gameObject;
                    if (!t.isAlive()) {
                        aliveCount--;
                        removeObject(gameObject);
                    }
                    if(t.hasReacedGoal()){
                        update(t.getCurrentScore());
                        removeObject(gameObject);
                    }
                }
                if (gameObject instanceof Projectile) {
                    Projectile p = (Projectile) gameObject;
                    if (!p.isAlive()) {
                        removeObject(gameObject);
                    }
                }

            } catch (java.util.ConcurrentModificationException e) {
                Throwable cause = e.getCause();
                System.out.println(cause.getMessage());
            }
        }
        removeObjectsFromGame();
        addObjectsToGame();
    }

    public synchronized void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject gameObject = objects.get(i);
            boolean shouldDraw = true;
            if (gameObject instanceof Troop) {
                Troop troop = (Troop) gameObject;
                if (!troop.isAlive() || troop.hasReacedGoal()) {
                    shouldDraw = false;
                }
            }
            if (shouldDraw) {
                Tile moveTo=null;
                moveTo = gameObject.getMoveToPosition();
                if(moveTo==null)
                    continue;

                PositionPair position = calculatePosition(gameObject, moveTo);


                double scale =gameObject instanceof Troop ? 0.4 : 0.7;
                scale = gameObject instanceof Projectile ? 0.2 : scale;


                double width = gameObject.getTilePosition().getSize().getWidth();
                double height = gameObject.getTilePosition().getSize().getHeight();

                Long troopSizeX = new Long(Math.round(width * scale));
                Long troopSizeY = new Long(Math.round(height * scale));
                //int x = Math.round(position.getX()*size+(size*progress));
                //int y = Math.round(position.getY()*size+(size*progress));

                int xOffset = (new Long(Math.round((width/2)-(troopSizeY/2)))).intValue();
                int yOffset = (new Long(Math.round((height/2)-(troopSizeX/2)))).intValue();

                if(gameObject instanceof Projectile){
                    Projectile p = (Projectile) gameObject;
                    position = calculatePosition(gameObject, p.getTarget());
                }

                g.drawImage(gameObject.getImage(),position.getX().intValue()+xOffset, position.getY().intValue()+yOffset, troopSizeX.intValue(), troopSizeY.intValue(),null);
                    //g.fillRect(x_current.intValue()+xOffset, y_current.intValue()+yOffset, troopSizeX.intValue(), troopSizeY.intValue());

                /*
                if(gameObject instanceof Projectile){
                    if(x_global<=0 && y_global <= 0){
                        ((Projectile)gameObject).damage();
                        removeObject(gameObject);
                    }
                    else if(!((Projectile) gameObject).aliveTarget()){
                        removeObject(gameObject);
                    }
                }
                */

            }
        }
    }

    private PositionPair calculatePosition(GameObject thisGO, Tile moveTo) {
        int sizeX = (int) thisGO.getTilePosition().getSize().getWidth();
        int sizeY = (int) thisGO.getTilePosition().getSize().getHeight();

        Position position = thisGO.getTilePosition().getPosition();
        double x_start = (position.getX() * sizeX) * 1.0;
        double y_start = (position.getY() * sizeY) * 1.0;

        double x_to = (moveTo.getPosition().getX() * sizeX) * 1.0;
        double y_to = (moveTo.getPosition().getY() * sizeY) * 1.0;

        Double progress = (thisGO.getMoveProgres() * 1.0) / 100.0;
        double x_global = x_start - x_to;
        double y_global = y_start - y_to;

        Long x_current = Math.round(x_start - (x_global * progress.doubleValue()));
        Long y_current = Math.round(y_start - (y_global * progress.doubleValue()));



        return new PositionPair(new Long(x_current), new Long(y_current));
    }

    private PositionPair calculatePosition(GameObject thisGO, GameObject moveTo) {
        PositionPair moveToPosition = calculatePosition(moveTo, moveTo.getMoveToPosition());

        int sizeX = (int) thisGO.getTilePosition().getSize().getWidth();
        int sizeY = (int) thisGO.getTilePosition().getSize().getHeight();

        Position position = thisGO.getTilePosition().getPosition();
        double x_start = (position.getX() * sizeX) * 1.0;
        double y_start = (position.getY() * sizeY) * 1.0;

        double x_to = (moveToPosition.getX()) * 1.0;
        double y_to = (moveToPosition.getY()) * 1.0;

        Double progress = (thisGO.getMoveProgres() * 1.0) / 100.0;
        double x_global = x_start - x_to;
        double y_global = y_start - y_to;

        Long x_current = Math.round(x_start - (x_global * progress.doubleValue()));
        Long y_current = Math.round(y_start - (y_global * progress.doubleValue()));



        return new PositionPair(new Long(x_current), new Long(y_current));
    }

    /**
     * Resets the game world.
     * <br /> <br />
     * <b>**Caution**</b> <br />
     * Should only be called in <b>tick()</b> method for thread safety
     */
    public synchronized void resetGame() {
        objects.clear();
        aliveTroops.clear();
        towers.clear();
        aliveCount=0;
    }


    /**
     * Get accumulated score.
     * <br /><br />
     * <b>**Note**</b><br />
     * The score is restored when <b>reset()</b> method is called.
     * @return
     */
    public synchronized int getVictoryScore() {
        return score;
    }

    void resetScore(){
        score=0;
    }

    private void update(int credit){
        setChanged();
        notifyObservers(credit);
    }

}
