package AntiTD;

import AntiTD.tiles.Tile;

import java.io.IOException;

/**
 * Created by mattias on 2015-11-27.
 */
public class Environment implements Runnable{

    Tile[][] level;

    @Override
    public void run() {
        try {
            new ReadXML();
        } catch (IOException e) {
            System.out.println("det funkar icke");
        }

    }
}
