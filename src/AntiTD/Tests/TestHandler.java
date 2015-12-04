package AntiTD.Tests;

import AntiTD.Handler;
import AntiTD.Position;
import AntiTD.tiles.PathTile;
import AntiTD.tiles.Tile;
import AntiTD.tiles.TowerTile;
import AntiTD.towers.BasicTower;
import AntiTD.towers.Tower;
import AntiTD.troops.BasicTroop;
import AntiTD.troops.Troop;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

/**
 * Created by Rallmo on 2015-12-04.
 */
public class TestHandler {
  Tower tower;
  Tile pos;
  Handler h;
  ImageIcon img;
  String hemma = "C:/Users/Rallmo/basictower.png";
  String skolan = "/home/id12/id12rdt/basictower.png";
  @Before
  public void setUp(){
    h = new Handler(40);

  }
  @Test
  public void testAddTower(){
    pos = new TowerTile(new Position(0,0));
    pos.setNeighbors(new Tile[]{pos});
    img = new ImageIcon(skolan);
    tower = new BasicTower(img, pos);
    tower.createTower(tower,pos);
    h.addObject(tower);
  }
  @Test
  public void testAddTroop(){
    Tile troopPs = new PathTile(new Position(0, 1));
    troopPs.setNeighbors(new Tile[]{pos});
    Troop t = new BasicTroop(troopPs);
    h.addObject(t);
  }
}
