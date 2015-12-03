package AntiTD.GameBackEnd;

import AntiTD.GameBackEnd.tiles.Tile;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public interface GameObject{

    public abstract void tick();
    public abstract void render(Graphics g);

    public abstract Image getImage();

    public abstract int getCurrentScore();

    public abstract Position getPosition();

    public abstract Tile getTilePosition();
}
