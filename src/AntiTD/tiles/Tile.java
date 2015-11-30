package AntiTD.tiles;

/**
 * Created by dv13trm on 2015-11-27.
 */
import AntiTD.Position;
public abstract class Tile {


    private boolean moveable;
    private boolean buildable;
    private Position position;


    public boolean setBuildable(boolean buildable) {
        this.buildable = buildable;
    }
    public boolean setMoveable(boolean moveable){
        this.moveable=moveable;
    }
    public boolean isMoveable() {
        return moveable;
    }
    public boolean isBuildable() {
        return buildable;
    }
    public void setPosition(Position position){
        this.position=position;
    }

}
