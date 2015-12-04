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
        g.fillRect((int)(getPosition().getX()*(getSize().getWidth())),
                (int)(getPosition().getY()*(getSize().getHeight())),
                (int)getSize().getWidth(),
                (int)getSize().getHeight());
    }
}
