package AntiTD;

import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import java.awt.*;

/**
 * Created by dv13tes on 2015-12-03.
 */
public class Dummy extends Troop implements GameObject {

    private float x;
    private float y;

    protected Dummy(Tile pos) {
        super(pos);
        setVelX(2);
        setVelY(2);
    }

    @Override
    public void tick() {
        x+=getVelX();
        y+=getVelY();
        if(x>400 || x < 0){
            setVelX(getVelX()*-1);
        }
        if(y>400 || y < 0){
            setVelY(getVelY()*-1);
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect((int)x, (int)y, 24, 24);
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public int getCurrentScore() {
        return 0;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public Tile getTilePosition() {
        return null;
    }
}
