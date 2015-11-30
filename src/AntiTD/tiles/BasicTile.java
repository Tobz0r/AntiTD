package AntiTD.tiles;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class BasicTile implements Tile {

    @Override
    public boolean isMoveable() {
        return false;
    }

    @Override
    public boolean isBuildable() {
        return false;
    }

    @Override
    public boolean isTeleport() {
        return false;
    }
}
