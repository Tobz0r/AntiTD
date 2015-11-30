package AntiTD;

import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mattias on 2015-11-27.
 */
public class Environment implements Runnable{

    Tile[][] level;

    @Override
    public void run() {
        try {
            ReadXML xmlReader = new ReadXML();
            ArrayList<Level> levels=xmlReader.getLevels();

        } catch (IOException e) {
            System.out.println("det funkar icke");
        }

    }
}
