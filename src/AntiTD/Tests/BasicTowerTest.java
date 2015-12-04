package AntiTD.Tests;

import AntiTD.Position;
import AntiTD.tiles.PathTile;
import AntiTD.tiles.Tile;
import AntiTD.tiles.TowerTile;
import AntiTD.towers.BasicTower;
import AntiTD.towers.FrostTower;
import AntiTD.towers.Tower;
import AntiTD.troops.BasicTroop;
import AntiTD.troops.Troop;
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
  Tile troopPos;
  ImageIcon img;
  String hemma = "C:/Users/Rallmo/basictower.png";
  String skolan = "/home/id12/id12rdt/basictower.png";

  @Before
  public void setUp(){
    pos = new TowerTile(new Position(0,0));
    pos.setNeighbors(new Tile[]{pos});

    troopPos = new PathTile(new Position(0,1));
    troopPos.setNeighbors(new Tile[]{troopPos});

    img = new ImageIcon(skolan);
    tower = new BasicTower(img, pos);
    tower.createTower(tower,pos);
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
  @Test
  public void testCheckIfUnitisClose(){
    Troop t = new BasicTroop(troopPos);

    assertEquals(tower.checkIfUnitIsClose(t), true);
  }
  @Test
  public void testDistance(){
    Troop t = new BasicTroop(troopPos);

    assertEquals(tower.distance(t), 1);
  }
  @Test
  public void testAttackThisAndgetMoneyIfKill(){
    Troop t = new BasicTroop(troopPos);
    tower.attack(t,tower.getDamage());

    assertEquals(t.isAlive(), false);
    assertEquals(tower.getCurrentScore(),1);
  }
  @Test
  public void testScanTarget(){

    Troop t = new BasicTroop(troopPos);

    tower.addTroopsToList(t);

    //tower.startShooting();
    tower.initScan();
    Troop troop = tower.getTarget();
    assertEquals(troop.getPosition(),t.getPosition());
  }
  @Test
  public void testAggroTarget(){
    Troop t = new BasicTroop(troopPos);

    tower.addTroopsToList(t);
    tower.aggroTarget();
    assertEquals(tower.getTarget(), null);
  }
  @Test
  public void testAggroTargetAndScanTarget(){
    Troop t = new BasicTroop(troopPos);

    tower.addTroopsToList(t);
    tower.initScan();
    assertEquals(tower.countUnitsInList(), 1);
    assertEquals(tower.getTarget().isAlive(), true);
    tower.aggroTarget();
    assertEquals(tower.getTarget().isAlive(), false);
    tower.aggroTarget();
    assertEquals(tower.getTarget(), null);
    assertEquals(tower.countUnitsInList(), 0);

  }
  @Test
  public void testStartShooting(){
    Troop t = new BasicTroop(troopPos);
    tower.addTroopsToList(t);
    tower.startShooting();
    assertEquals(tower.countUnitsInList(),1);
    tower.startShooting();
    tower.startShooting();
    assertEquals(tower.countUnitsInList(),0);
    assertEquals(tower.getTarget(), null);
  }
  @Test
  public void testOutOfRange(){
    pos = new TowerTile(new Position(0,0));
    pos.setNeighbors(new Tile[]{pos});

    img = new ImageIcon(skolan);
    tower = new BasicTower(img, pos);
    tower.createTower(tower,pos);

    Tile newPos = new PathTile(new Position(0,1));
    Troop t = new BasicTroop(newPos);

    tower.initScan();
    tower.initScan();

    assertEquals(tower.getTarget(), null);
    /*tower.setMoney(20);
    tower.buildTower();
    int frostTower = tower.countFrostTowerTypes();
    assertEquals(frostTower,1);
    Tower frost = tower.getFrostTower();
    tower.getFrostTower().initScan();
    tower.getFrostTower().initScan();

    assertEquals(tower.getFrostTower().getTarget(), null)*/;




  }


}
