package AntiTD.tiles;

import AntiTD.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Tobias Estefors
 * This tiled is used for to make paths
 * when paths colide.
 * Needs to use JunctionTiles before the connectionTile
 */
public class ConnectionTile extends Tile {
    public ConnectionTile() {
        this(null);
    }

    public ConnectionTile(Position position){
        super(position);
        setMoveable(true);
        setBuildable(false);
        try {
            setImage(ImageIO.read(new File("sprites/patheses.png")));
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
