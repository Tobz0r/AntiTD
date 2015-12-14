package AntiTD;

/**
 * Created by mattias on 2015-12-14.
 */
public interface MovableGameObject extends GameObject {
    int getMoveProgress();
    boolean isAlive();
}
