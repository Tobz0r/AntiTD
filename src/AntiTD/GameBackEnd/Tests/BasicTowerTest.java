package AntiTD.GameBackEnd.Tests;

import AntiTD.GameBackEnd.tiles.Tile;
import AntiTD.GameBackEnd.tiles.TowerTile;
import AntiTD.GameBackEnd.towers.BasicTower;
import AntiTD.GameBackEnd.towers.Tower;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rallmo on 2015-12-01.
 */
public class BasicTowerTest {

  Tower tower;
  Tile pos;
  ImageIcon img;

  @Before
  public void setUp(){
    pos = new TowerTile();
    pos.setNeighbors(new Tile[]{pos});
    img = new ImageIcon("C:/Users/Rallmo/basictower.png");
    tower = new BasicTower(img, pos);
    tower.createTower(tower,pos);
  }

  @Test
  public void testPosition(){
    Assert.assertEquals(tower.getPosition(), pos.getPosition());

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
  @Test
  public void testBuildMoreTower(){

    tower.setMoney(20);
    tower.buildTower();
    tower.buildTower();
    assertEquals(tower.getTowersLength(), 3);

  }
  @Test
  public void testBuildTowerTypes(){

    tower.setMoney(6);
    tower.buildTower();
    tower.buildTower();
    tower.buildTower();
    int frostTower = tower.countFrostTowerTypes();
    int basicTower = tower.countBasicTowerTypes();
    assertEquals(frostTower, 1);
    assertEquals(basicTower, 2);
  }


}
