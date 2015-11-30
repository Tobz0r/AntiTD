package AntiTD.tiles;

/**
 * Created by dv13trm on 2015-11-27.
 */
import AntiTD.Position;
public interface Tile {

    public boolean isMoveable();
    public boolean isBuildable();
    public boolean isTeleport();
}
