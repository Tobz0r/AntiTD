package AntiTD;

import AntiTD.tiles.Tile;

/**
 * Created by mattias on 2015-12-14.
 */
public interface MovableGameObject extends GameObject {
    int getMoveProgress();
    boolean isAlive();
    Tile getMoveToPosition();
}
