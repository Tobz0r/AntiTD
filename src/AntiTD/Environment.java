package AntiTD;

import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mattias on 2015-11-27.
 */
public class Environment implements Runnable{

    Tile[][] level;

    @Override
    public void run() {
            ReadXML xmlReader = new ReadXML(new File("levels.xml"));
            ArrayList<Level> levels=xmlReader.getLevels();
    }
}
