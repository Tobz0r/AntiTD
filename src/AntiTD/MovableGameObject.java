package AntiTD;

import AntiTD.tiles.Tile;

/**
 * Created by id12men on 2015-12-14.
 */
public interface MovableGameObject extends GameObject {

    /**
     * Get the move progress between two <b>Tile</b> positions.
     * @return a value between 0 and 99. A value over this translates to a new <b>Tile</b> position and this
     * value will be reset to 0.
     */
    int getMoveProgress();

    /**
     * Get if object is alive or not.
     * @return true if alive, else false.
     */
    boolean isAlive();

    /**
     * Get the <b>Tile</b> position which this object is moving to.
     * @return position as <b>Tile</b>
     */
    Tile getMoveToPosition();

    /**
     * Get if this object has reached its destination.
     * @return true if reached, else false.
     */
    boolean hasReachedGoal();
}
