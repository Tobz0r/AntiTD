package AntiTD;

import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by dv13tes on 2015-11-27.
 */
public interface GameObject{

    void tick();

    Image getImage();

    int getCurrentScore();


    Position getPosition();
    String type();

    Tile getTilePosition();
    Tile getMoveToPosition();
    int getMoveProgress();

}
