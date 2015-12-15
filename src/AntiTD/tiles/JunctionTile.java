package AntiTD.tiles;

import AntiTD.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Tobias Estefors
 * Tile to be used before any crossroad so the crossroad wont point back
 */
public class JunctionTile extends Tile {

    public JunctionTile() {
        this(null);
    }

    public JunctionTile(Position pos){
        super(pos);
        setBuildable(false);
        setMoveable(true);
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
