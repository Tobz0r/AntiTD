package AntiTD.tiles;

/**
 * Created by mattias on 2015-11-27.
 */
public class TowerTile implements AntiTD.tiles.Tile {

    private String[][] level = null;

    public TowerTile(String[][] level){
        this.level = level;
    }
    public boolean isWalkable(){
        return true;
    }
    public boolean isBUildable(String str){
        if(str.equals("1")) {
            return true;
        }else{
            return false;
        }
    }
    public boolean isTeleportTile(){
        return true;
    }
}
