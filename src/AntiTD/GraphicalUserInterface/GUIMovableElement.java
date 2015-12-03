package AntiTD.GraphicalUserInterface;

/**
 * Created by mattias on 2015-12-03.
 */
public interface GUIMovableElement {

    public abstract boolean isMoving();

    public abstract int getMoveProgres();

    public abstract int getX();

    public abstract int getY();
}
