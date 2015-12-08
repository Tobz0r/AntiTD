package AntiTD.troops;

import AntiTD.*;
import AntiTD.tiles.GoalTile;
import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by dv13trm on 2015-11-27.
 */
public abstract class Troop implements GameObject {
    //private static int victoryScore;
    protected int health;
    protected int score;
    protected double speed;
    private Image img;
    private Stack<Tile> history;
    private Tile nextTile;
    private double moveProgres;
    private boolean hasReacedGoal;
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
        if (!hasReacedGoal && isAlive()) {
            if (!this.isMoving) {
                this.isMoving = true;
                this.moveProgres = speed;
                this.nextTile = getNextTile();
            }

            if (this.moveProgres < 100) {
                this.moveProgres += speed;
                if (this.moveProgres > 100) {
                    this.moveProgres = 100;
                }
            } else {
                this.isMoving = false;
                this.moveProgres = 0;

                history.push(nextTile);
                if (nextTile instanceof GoalTile) {
                    hasReacedGoal = true;
                } else if (nextTile.isTeleporter()) {
                    history.push(nextTile.getTeleportTo());
                }
            }
        }
        /*
        if(hasReacedGoal ){
            victoryScore++;
            Handler.removeObject(this);
        }
        else if(!isAlive()){
            Handler.removeObject(this);
        }
        */
    }
    /*
    public static int getVictoryScore(){
        return victoryScore;

    }
    public static void resetScore(){
        victoryScore=0;
    }
    */

    @Override
    public Tile getMoveToPosition() {
        return nextTile;
    }

    @Override
    public int getMoveProgres() {
        Long p = Math.round(this.moveProgres);
        return p.intValue();
    }

    private Tile getNextTile() {
        Tile[] neigbors;
        neigbors = history.peek().getNeighbors2();

        Tile nextTile = null;

        for (Tile tile : neigbors) {
            if (tile.isMoveable()) {
                if (history.search(tile) == -1) {
                    nextTile = tile;
                    break;
                }
            }
        }
        /*
        if (nextTile.isTeleporter()) {
            nextTile = nextTile.getTeleportTo();
        }*/
        return nextTile;
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public int getCurrentScore() {
        if (hasReacedGoal) {
            return score;
        } else {
            return 0;
        }
    }

    public boolean hasReacedGoal() {
        return hasReacedGoal;
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
        if ( ! hasReacedGoal ) {
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
        if (health <= 0) {
            isAlive = false;
        }
        if (hasReacedGoal) {
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
        this.speed = (speed * 0.5);
        slowed = true;
        System.out.println(speed);
    }
    public boolean isSlowed(){
        return slowed;
    }
}
