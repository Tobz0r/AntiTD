package AntiTD.tiles;

import AntiTD.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Tobias Estefors
 * Tile used to build towers on
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
            setImage(ImageIO.read(new File("sprites/grass.png")));
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
