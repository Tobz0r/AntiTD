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
            ReadXML xmlReader = new ReadXML();
            String[][] map = xmlReader.getMap();

            for(int y = 0; y < map.length; y++) {
                for(int x = 0; x < map[0].length; x++) {
                    System.out.print(map[y][x]);
                }
                System.out.println("");
            }

        } catch (IOException e) {
            System.out.println("det funkar icke");
        }

    }
}
