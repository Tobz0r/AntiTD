package AntiTD.Tests;
import AntiTD.Animation;
import AntiTD.Handler;
import AntiTD.Position;
import AntiTD.ReadXML;
import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.tiles.TowerTile;
import AntiTD.towers.Tower;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Rallmo on 2015-12-04.
 */
public class TestAnimation {
  String skolan = "/home/id12/id12rdt/basictower.png";
  LinkedList<Tower> towersInGame = new LinkedList< Tower>();
  Tile pos;
  Handler handler;
  private ArrayList<Level> levels;
  Level level;
  Tile[][] map;
  int mapNr = 0;

  @Before
  public void setUp(){
    pos = new TowerTile(new Position(0,0));
    handler=new Handler(0);
    ReadXML xmlReader = new ReadXML(new File("levels.xml"));
    levels=xmlReader.getLevels();
    level=levels.get(mapNr);
    map=level.getMap();

    Level.setCurrentMap(map);
;

  }
  @Test
  public void loadImg(){


  }
}
