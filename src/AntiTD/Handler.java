package AntiTD;

import AntiTD.tiles.Tile;
import AntiTD.towers.Projectile;
import AntiTD.towers.Tower;
import AntiTD.troops.Troop;

import java.awt.*;
import java.util.*;

/**
 * @author Tobias Estefors, Mattias Edin
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
    private Sounds sounds = new Sounds();
    private boolean isPaused=false;

    private Object lock = new Object();



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
       // return aliveTroops.size() > 0;
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
                    t.removeTroopFromList((Troop) object);
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

    /**
     * Returns an arraylist of alive troops
     * @return list of alive troops
     */
    public synchronized ArrayList<Troop> getAliveTroops() {
        ArrayList<Troop> list = new ArrayList<Troop>(aliveTroops.size());
        for (GameObject go : aliveTroops) {
            list.add((Troop) go);
        }
        return list;
    }

    public synchronized boolean getIfGameIsChanging() {

        return true;
    }

    /**
     * Gets called each timetick from environment. Updates the gamestate.
     */
    public synchronized void tick() {
        for (GameObject gameObject : objects) {
            try {
                gameObject.tick();
                if (gameObject instanceof MovableGameObject) {
                    score += gameObject.getCurrentScore();
                    MovableGameObject mgo = (MovableGameObject) gameObject;
                    if (!mgo.isAlive()) {
                        removeObject(gameObject);
                        if (mgo instanceof Troop) {
                            aliveCount--;
                            if(!isPaused) {
                                System.out.println(isPaused);
                                sounds.music("music/deadman.wav", false);
                            }
                        }
                    }
                    if(mgo.hasReachedGoal()){
                        update(mgo.getCurrentScore());
                        removeObject(gameObject);
                    }
                }
            } catch (java.util.ConcurrentModificationException e) {
                Throwable cause = e.getCause();
                System.out.println(cause.getMessage());
            }
        }
        removeObjectsFromGame();

        if (resetFlag) {
            objects.clear();
            aliveTroops.clear();
            towers.clear();
            synchronized (lock) {
                resetFlag = false;
            }
        }

        addObjectsToGame();
    }

    /**
     * Draws the current gamestate on the board for each timetick
     * @param g the boards graphics
     */
    public synchronized void render(Graphics g) {
        for (GameObject gameObject : objects) {

            double scale =gameObject instanceof Troop ? 0.4 : 0.7;
            scale = gameObject instanceof Projectile ? 0.2 : scale;


            double width = gameObject.getTilePosition().getSize().getWidth();
            double height = gameObject.getTilePosition().getSize().getHeight();

            Long troopSizeX = Math.round(width * scale);
            Long troopSizeY = Math.round(height * scale);

            int xOffset = (new Long(Math.round((width/2)-(troopSizeY/2)))).intValue();
            int yOffset = (new Long(Math.round((height/2)-(troopSizeX/2)))).intValue();

            PositionPair position = new PositionPair(
                    (long)(gameObject.getTilePosition().getPosition().getX()*width),
                    (long)(gameObject.getTilePosition().getPosition().getY()*height));

            if (gameObject instanceof MovableGameObject) {
                MovableGameObject mgo = (MovableGameObject) gameObject;
                Tile moveTo = mgo.getMoveToPosition();
                position = calculatePosition(mgo, moveTo);
                if(mgo instanceof Projectile){
                    Projectile p = (Projectile) mgo;
                    position = calculatePosition(mgo, p.getTarget());
                }
            }

            g.drawImage(
                gameObject.getImage(),
                position.getX().intValue() + xOffset,
                position.getY().intValue() + yOffset,
                troopSizeX.intValue(),
                troopSizeY.intValue(),
                null);
        }
    }

    private PositionPair calculatePosition(MovableGameObject thisGO, double moveToXpx, double moveToYpx) {
        int sizeX = (int) thisGO.getTilePosition().getSize().getWidth();
        int sizeY = (int) thisGO.getTilePosition().getSize().getHeight();

        Position position = thisGO.getTilePosition().getPosition();
        double x_start = (position.getX() * sizeX) * 1.0;
        double y_start = (position.getY() * sizeY) * 1.0;

        double x_to = moveToXpx;
        double y_to = moveToYpx;

        Double progress = (thisGO.getMoveProgress() * 1.0) / 100.0;
        double x_global = x_start - x_to;
        double y_global = y_start - y_to;

        Long x_current = Math.round(x_start - (x_global * progress));
        Long y_current = Math.round(y_start - (y_global * progress));

        return new PositionPair(x_current, y_current);
    }

    private PositionPair calculatePosition(MovableGameObject thisGO, Tile moveTo) {
        Position p = moveTo.getPosition();
        Dimension size = moveTo.getSize();
        return calculatePosition(thisGO, ( p.getX() * size.getWidth() ), ( p.getY() * size.getHeight() ));
    }

    private PositionPair calculatePosition(MovableGameObject thisGO, MovableGameObject moveTo) {
        PositionPair moveToPosition = calculatePosition(moveTo, moveTo.getMoveToPosition());
        return calculatePosition(thisGO, moveToPosition.getX(), moveToPosition.getY());
    }

    /**
     * Resets the game world.
     * <br /> <br />
     * <b>**Caution**</b> <br />
     * Should only be called in <b>tick()</b> method for thread safety
     */
    public void resetGame() {
        synchronized (lock) {
            resetFlag = true;
            aliveCount=0;
            //lock.notifyAll();
        }
        /*
        objects.clear();
        aliveTroops.clear();
        towers.clear();
        */
    }


    /**
     * Get accumulated score.
     * <br /><br />
     * <b>**Note**</b><br />
     * The score is restored when <b>resetScore()</b> method is called.
     * @return the score
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
    public void setIsPaused(boolean paused){
        this.isPaused=paused;
    }
}
