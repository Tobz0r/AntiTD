package AntiTD;

import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Tobias Estefors
 */
public interface GameObject{
    /**
     * Method to be called each timetick for every gameobject
     */
    void tick();

    /**
     * Getter for the image of gameobjects
     * @return this gameobjets image
     */
    Image getImage();

    /**
     * Returns how much this gameobject is worth in score
     * @return this gameobjects score
     */
    int getCurrentScore();

    /**
     * Returns this gameobjects position
     * @return a position of this object
     */
    Position getPosition();

    /**
     * Returns this gameobjects tiles
     * @return a tileposition of this object
     */
    Tile getTilePosition();


}