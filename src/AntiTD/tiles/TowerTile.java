package AntiTD.tiles;

import AntiTD.Position;

import java.awt.*;

/**
 * Created by mattias on 2015-11-27.
 */
public class TowerTile extends Tile {
    public TowerTile() {
        this(null);
    }
    public TowerTile(Position pos){
        super(pos); 
        setBuildable(true);
        setMoveable(false);
    }

    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)(getPosition().getX()*(getSize().getWidth())),(int)(getPosition().getY()*(getSize().getHeight())),(int)getSize().getWidth(),(int)getSize().getHeight());
    }
}
