package AntiTD;

import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by dv13tes on 2015-11-27.
 */
public interface GameObject{

    /**
     * Fixed time step update of object. Updates the object when called.
     */
    void tick();

    /**
     * Gets the assigned image of the object.
     * @return image object.
     */
    Image getImage();

    /**
     * Get the current score of the object that it has accumulated since created.
     * @return the score
     */
    int getCurrentScore();

    /**
     * Get the coordinates of current position.
     * @return the coordinates
     */
    Position getPosition();

    /**
     * Get current position as <b>Tile</b> object.
     * @return the position
     */
    Tile getTilePosition();
}
