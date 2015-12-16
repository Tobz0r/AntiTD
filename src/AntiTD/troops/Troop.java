package AntiTD.troops;

import AntiTD.*;
import AntiTD.tiles.GoalTile;
import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.Stack;

/**
 * Created by dv13trm on 2015-11-27.
 */
public abstract class Troop implements MovableGameObject {
    //private static int victoryScore;
    protected int health;
    protected int score;
    protected double speed;
    private Image img;
    private Stack<Tile> history;
    private Tile nextTile;
    private double moveProgres;
    private boolean hasReachedGoal;
    private boolean isMoving;
    private boolean slowed;




    protected Troop(Tile pos) {
        this(null, pos);
    }

    protected Troop(Image img, Tile pos) {
        this(pos, 1, 1, 1);
    }
    protected Troop(Tile pos, int health, int score, double speed) {
        this(null, pos, health, score, speed);
    }

    protected Troop(Image img, Tile pos, int health, int score, double speed) {
        this.img = img;
        this.health = health;
        this.score = score;
        this.speed = speed;
        this.history = new Stack<Tile>();
        this.history.push(pos);
        this.nextTile = getNextTile();
        slowed = false;



    }

    @Override
    public abstract void tick();

    /**
     * ** CAUTION **
     * Should be used in implemented tick method and no where else.
     *
     * Moves the troop to next tile according to speed when accumulated
     * speed reaches the value of 100 the position will be updated.
     */
    protected void move() {
        if (!hasReachedGoal && isAlive()) {
            if (!this.isMoving) {
                this.isMoving = true;
                this.moveProgres = speed;
                this.nextTile = getNextTile();
            }

            if (this.moveProgres < 100) {
                this.moveProgres += speed;
                /*if (this.moveProgres > 100) {
                    this.moveProgres = 100;
                }*/
            } else {
                this.isMoving = false;
                this.moveProgres = 0;

                history.push(nextTile);
                if (nextTile instanceof GoalTile) {
                    hasReachedGoal = true;
                } else if (nextTile.isTeleporter()) {
                    Tile endTPTile = nextTile.getTeleportTo();

                    // Add all tiles between end and start teleport in history
                    Tile tempTile = getNextTile();
                    while (endTPTile != getTilePosition() ) {
                        history.push(tempTile);
                        tempTile = getNextTile();
                    }

                }
            }
        }
    }


    @Override
    public Tile getMoveToPosition() {
        return nextTile;
    }

    @Override
    public int getMoveProgress() {
        Long p = Math.round(this.moveProgres);
        return p.intValue();
    }

    private Tile getNextTile() {
        Tile[] neigbors;
        neigbors = history.peek().getNeighbors();

        Tile returnTile = null;

        for (Tile tile : neigbors) {
            if (tile.isMoveable()) {
                if (history.search(tile) == -1) {
                    returnTile = tile;
                    break;
                }
            }
        }
        return returnTile;
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public int getCurrentScore() {
        if (hasReachedGoal) {
            return score;
        } else {
            return 0;
        }
    }

    public boolean hasReachedGoal() {
        return hasReachedGoal;
    }

    public int getHealth() {
        return this.health;
    }

    /**
     * Attacks this troop
     *
     * @param damage amount of damage to take
     * @return true if this troop died else false
     */
    public boolean attackThis(int damage) {
        if ( !hasReachedGoal() ) {
            health = health - damage;
            return !this.isAlive();
        } else {
            return false;
        }
    }

    /**
     * Checks troops life status
     *
     * @return true if alive else false
     */
    public boolean isAlive() {
        boolean isAlive = true;
        if (health < 1) {
            isAlive = false;
        }
        if (hasReachedGoal) {
            isAlive = false;
        }
        return isAlive;
    }

    @Override
    public Position getPosition() {
        return history.peek().getPosition();
    }

    @Override
    public Tile getTilePosition() {
        return history.peek();
    }
    public String type(){
        return "Troop";
    }

    public void slowSpeed(){
        if (! this.isSlowed()) {
            this.speed = (speed * 0.5);
            slowed = true;
        }
    }
    public boolean isSlowed(){
        return slowed;
    }


}