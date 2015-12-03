package AntiTD.GameBackEnd.tiles;

import AntiTD.GameBackEnd.Position;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class CrossroadTile extends Tile {

    public CrossroadTile() {
        this(null);
    }
    public CrossroadTile(Position pos){
        super(pos);
        setBuildable(false);
        setMoveable(true);
    }

    @Override
    public String toTileCode() {
        return "C";
    }

    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(getPosition().getX()*48,getPosition().getY()*48,getSize(),getSize());
    }
}
