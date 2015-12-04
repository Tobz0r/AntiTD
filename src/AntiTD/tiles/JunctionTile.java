package AntiTD.tiles;

import AntiTD.Position;

import java.awt.*;

/**
 * Created by dv13tes on 2015-12-04.
 */
public class JunctionTile extends Tile {

    public JunctionTile() {
        this(null);
    }

    public JunctionTile(Position pos){
        super(pos);
        setBuildable(false);
        setMoveable(true);
    }
    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.orange);
    }
}
