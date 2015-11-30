package AntiTD;

import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mattias on 2015-11-27.
 */
public class Environment implements Runnable, Observer {

    private ArrayList<Level> levels;
    private Handler handler;
    public Environment(){
        handler=new Handler();
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();
    }

    @Override
    public void run() {

    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
