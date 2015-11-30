package AntiTD.tiles;

/**
 * Created by dv13trm on 2015-11-27.
 */
import AntiTD.Position;
public abstract class Tile {

    public boolean isMoveable() {
        return moveable;
    }

    private boolean moveable;


    public boolean isBuildable() {
        return buildable;
    }

    private boolean buildable;



    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }
    public void setMoveable(boolean moveable){
        this.moveable=moveable;
    }
}
