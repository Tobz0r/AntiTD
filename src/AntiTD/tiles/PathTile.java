package AntiTD.tiles;

import AntiTD.tiles.*;

/**
 * Created by mattias on 2015-11-27.
 */
public class PathTile implements AntiTD.tiles.Tile {

    private Tile[][] level = null;

    public PathTile(Tile[][] level){
        this.level = level;
    }
    public boolean isWalkable(){
        return true;
    }
    public boolean isBUildable(String str){
        return true;
    }
    public boolean isTeleportTile(){
        return true;
    }
}
