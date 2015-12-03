package AntiTD.tiles;

import AntiTD.Position;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class StartTile extends Tile {

    public StartTile() {
        this(null);
    }

    public StartTile(Position pos){
        super(pos);
        setBuildable(false);
        setMoveable(true);
    }

    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.pink);
        g.fillRect(getPosition().getX()*48,getPosition().getY()*48,getSize(),getSize());
    }
}
