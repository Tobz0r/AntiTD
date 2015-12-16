package AntiTD.tiles;

import AntiTD.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Tobias Estefors
 * Tile used as goal, can only be walkable and only 1 per map is recomended.
 */
public class GoalTile extends Tile {
    private BufferedImage goalFort;
    public GoalTile() {
        this(null);
    }

    public GoalTile(Position pos){
        super(pos);
        setBuildable(false);
        setMoveable(true);
        try {
            setImage(ImageIO.read(new File("sprites/goal.png")));
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
