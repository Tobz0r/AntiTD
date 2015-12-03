package AntiTD.GameBackEnd.tiles;

import AntiTD.GameBackEnd.Position;

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
    public String toTileCode() {
        return "B";
    }

    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getPosition().getX()*48,getPosition().getY()*48,getSize(),getSize());
    }
}
