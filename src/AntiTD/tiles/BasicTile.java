package AntiTD.tiles;

import AntiTD.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Tobias Estefors
 * This tile if used only for visual, it can neither be built not walked on
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

    /**
     * Draws the tile on the board
     * @param g the board graphics
     */
    @Override
    public void landOn(Graphics g) {
        g.drawImage(getImage(),(int)(getPosition().getX()*(getSize().getWidth())),
                (int)(getPosition().getY()*(getSize().getHeight())),
                (int)getSize().getWidth(),
                (int)getSize().getHeight(),null);
    }

}
