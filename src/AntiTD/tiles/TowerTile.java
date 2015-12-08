package AntiTD.tiles;

import AntiTD.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by mattias on 2015-11-27.
 */
public class TowerTile extends Tile {
    public TowerTile() {
        this(null);
    }
    public TowerTile(Position pos){
        super(pos);
        setBuildable(true);
        setMoveable(false);
        try {
            setImage(ImageIO.read(new File("grass.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void landOn(Graphics g) {
        g.drawImage(getImage(),(int)(getPosition().getX()*(getSize().getWidth())),
                (int)(getPosition().getY()*(getSize().getHeight())),
                (int)getSize().getWidth(),
                (int)getSize().getHeight(),null);
    }
}
