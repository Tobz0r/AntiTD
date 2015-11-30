package AntiTD.tiles;

/**
 * Created by dv13trm on 2015-11-27.
 */
import AntiTD.Position;
public abstract class Tile {

    

    public abstract boolean isMoveable();
    public abstract boolean isBuildable();
    public abstract boolean isTeleport();
}
