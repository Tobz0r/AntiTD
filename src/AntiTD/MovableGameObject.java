package AntiTD;

import AntiTD.tiles.Tile;

/**
 * Created by mattias on 2015-12-15.
 */
public interface MovableGameObject extends GameObject {

    /**
     * Checks how far the unit has walked
     *
     * @return moveprogress for troops
     */
    int getMoveProgress();

    /**
     * Returns this gameobjects next tiles
     * @return a tileposition of this object
     */
    Tile getMoveToPosition();

    boolean hasReachedGoal();

    boolean isAlive();
}
