package AntiTD.Tests;

import AntiTD.tiles.Tile;
import AntiTD.tiles.TowerTile;
import AntiTD.towers.BasicTower;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rallmo on 2015-12-01.
 */
public class BasicTowerTest {

  BasicTower tower;
  Tile pos;
  ImageIcon img;

  @Before
  public void setUp(){
    pos = new TowerTile();
    pos.setNeighbors(new Tile[]{pos});
    img = new ImageIcon("C:/Users/Rallmo/basictower.png");
    tower = new BasicTower(img, pos);
    tower.createTower(img,pos);
  }

  @Test
  public void testPosition(){
    assertEquals(tower.getPosition(), pos.getPosition());

  }


  @Test
  public void testDamage(){
    assertEquals(tower.getDamage(), 5);
  }

  @Test
  public void testIfInList(){
    assertEquals(tower.getTowers(), false);
  }

  @Test
  public void testBuildTower(){
    tower.setMoney(50);
    tower.buildTower();
    assertEquals(tower.getCurrentScore(), 45);
  }


}
