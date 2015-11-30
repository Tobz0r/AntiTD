package AntiTD.tiles;

/**
 * Created by dv13trm on 2015-11-27.
 */
import AntiTD.Position;
public interface Tile {

    public boolean isWalkable();
    public boolean isBUildable(String str);
    public boolean isTeleportTile();


}
