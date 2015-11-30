package AntiTD.tiles;

/**
 * Created by mattias on 2015-11-27.
 */
public class TowerTile implements Tile {
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
