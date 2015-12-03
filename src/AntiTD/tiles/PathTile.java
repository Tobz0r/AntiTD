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
    public void landOn(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(getPosition().getX()*48,getPosition().getY()*48,getSize(),getSize());
    }
}
