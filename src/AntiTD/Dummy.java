package AntiTD;

import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import java.awt.*;

/**
 * Created by dv13tes on 2015-12-03.
 */
public class Dummy extends Troop implements GameObject {

    private float velX=8;
    private float velY=2;
    private float x=2;
    private float y=2;

    protected Dummy(Tile pos) {
        super(pos);
    }


    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


    @Override
    public void tick() {
        x+=velX;
        y+=velY;
        if(x>400 || x < 0){
            velX*=-1;
        }
        if(y>400 || y < 0){
            velY*=-1;
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
