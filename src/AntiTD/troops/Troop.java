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

    private int health;
    private int score;
    private Image img;
    private Stack<Tile> history;
    private boolean hasReacedGoal;

    protected Troop(Tile pos) {
        this(null, pos);
    }

    protected Troop(Image img, Tile pos) {
        this.img = img;
        this.score = 0;
        this.history = new Stack<Tile>();
        this.history.push(pos);
    }

    @Override
    public abstract void tick();

    protected void move() {
        if (hasReacedGoal == false && this.isAlive()) {
            ArrayList<Tile> neigbors = history.peek().getNeighbors();
            Tile nextTile = null;

            for (Tile tile : neigbors) {
                if (tile.isMoveable()) {
                    if (history.search(tile) != -1) {
                        nextTile = tile;
                        break;
                    }
                }
            }
            if (nextTile.isTeleporter()) {
                nextTile = nextTile.getTeleportTo();
            }

            if (nextTile instanceof GoalTile) {
                hasReacedGoal = true;
            }

            history.push(nextTile);
        }
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public int getCurrentScore() {
        if (hasReacedGoal && this.isAlive()) {
            return score;
        } else {
            return 0;
        }
    }

    /**
     * Attacks this troop
     * @param damage amount of damage to take
     * @return true if this troop died else false
     */
    public boolean attackThis(int damage) {
        health = health - damage;
        return !this.isAlive();
    }

    /**
     * Checks troops life status
     * @return true if alive else false
     */
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public Tile getPosition() {
        return history.peek();
    }
}
