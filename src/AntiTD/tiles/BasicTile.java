package AntiTD.tiles;

import AntiTD.Position;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class BasicTile extends Tile {

    public BasicTile(){
        this(null);
    }
    public BasicTile(Position pos){
        super(pos);
        setBuildable(false);
        setMoveable(false);
    }


    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.black);
        g.fillRect((int)(getPosition().getX()*(getSize().getWidth())),(int)(getPosition().getY()*(getSize().getHeight())),(int)getSize().getWidth(),(int)getSize().getHeight());
    }
}
