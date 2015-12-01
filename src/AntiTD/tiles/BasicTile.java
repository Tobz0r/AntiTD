package AntiTD.tiles;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class BasicTile extends Tile {
    public BasicTile(){
        super();
        setBuildable(false);
        setMoveable(false);
    }


    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getPosition().getX()*48,getPosition().getY()*48,getSize(),getSize());
    }
}
