package AntiTD.tiles;

import AntiTD.Position;
import AntiTD.tiles.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by mattias on 2015-11-27.
 */
public class PathTile extends Tile {

    private BufferedImage teleporter;

    public PathTile(){
        this(null);
    }
    public PathTile(Position pos){
        super(pos);
        setMoveable(true);
        setBuildable(false);
        try {
            setImage(ImageIO.read(new File("sprites/patheses.png")));
            teleporter=ImageIO.read(new File("sprites/teleporter_active.gif"));
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
        if(isTeleporterimage()){
            g.drawImage(teleporter,(int)(getPosition().getX()*(getSize().getWidth())),
                    (int)(getPosition().getY()*(getSize().getHeight())),
                    (int)getSize().getWidth(),
                    (int)getSize().getHeight(),null);
        }
    }
}
