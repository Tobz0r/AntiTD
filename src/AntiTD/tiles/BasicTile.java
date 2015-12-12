package AntiTD.tiles;

import AntiTD.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
        try {
            setImage(ImageIO.read(new File("sprites/rock.png")));
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
