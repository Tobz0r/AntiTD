package AntiTD;

import AntiTD.tiles.Tile;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public interface GameObject{

    public abstract void tick();

    public abstract Image getImage();

    public abstract int getCurrentScore();

    public abstract Position getPosition();
}
