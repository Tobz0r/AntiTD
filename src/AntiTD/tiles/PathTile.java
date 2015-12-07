package AntiTD.tiles;

import AntiTD.Position;
import AntiTD.tiles.*;

import java.awt.*;

/**
 * Created by mattias on 2015-11-27.
 */
public class PathTile extends Tile {

    public PathTile(){
        this(null);
    }
    public PathTile(Position pos){
        super(pos);
        setMoveable(true);
        setBuildable(false);
    }

    @Override
    public String toString(){
        return "Tile X: "+getPosition().getX()+" Y: "+getPosition().getY();
    }

    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect((int)(getPosition().getX()*(getSize().getWidth())),
                (int)(getPosition().getY()*(getSize().getHeight())),
                (int)getSize().getWidth(),
                (int)getSize().getHeight());    }
}
