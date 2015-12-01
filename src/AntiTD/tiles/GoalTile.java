package AntiTD.tiles;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class GoalTile extends Tile {

    public GoalTile(){
        super();
        setBuildable(false);
        setMoveable(true);
    }

    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(getPosition().getX()*48,getPosition().getY()*48,getSize(),getSize());
    }
}
