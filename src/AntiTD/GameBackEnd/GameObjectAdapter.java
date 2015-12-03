package AntiTD.GameBackEnd;

import AntiTD.GraphicalUserInterface.GUIMovableElement;

/**
 * Created by mattias on 2015-12-03.
 */
public class GameObjectAdapter implements GUIMovableElement {
    private int x;
    private int y;
    private boolean isMoving;
    private int moveProgres;

    public GameObjectAdapter(GameObject go) {
        this.x = go.getTilePosition().getPosition().getX();
        this.y = go.getTilePosition().getPosition().getY();
        if (moveProgres > 0) {
            isMoving = true;
        }
        this.moveProgres = moveProgres;
    }

    @Override
    public boolean isMoving() {
        return isMoving;
    }

    @Override
    public int getMoveProgres() {
        return moveProgres;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}