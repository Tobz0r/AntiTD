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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rallmo on 2015-12-01.
 */

public class BasicTowerTest {

  Tower tower;
  Tile pos;
  Tile troopPos;
  ImageIcon img;
  Troop troop;
  String hemma = "C:/Users/Rallmo/basictower.png";
  String skolan = "/home/id12/id12rdt/basictower.png";
  ArrayList<Troop> troops = new ArrayList<>();
  @Before
  public void setUp(){
    pos = new TowerTile(new Position(0,0));
    pos.setNeighbors(new Tile[]{pos});

    troopPos = new PathTile(new Position(0,1));
    troopPos.setNeighbors(new Tile[]{troopPos});
    troop = new BasicTroop(troopPos);
    troops.add(troop);
    img = new ImageIcon(skolan);
    //tower = new BasicTower(img, pos,troops);
   // tower.createTower(tower,pos);
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

    //Troop t = new BasicTroop(troopPos);

    //tower.addTroopsToList(t);

    //tower.startShooting();
    tower.initScan();
    Troop t = tower.getTarget();
    assertEquals(t.getPosition(),troop.getPosition());
  }
  @Test
  public void testAggroTarget(){
    tower.aggroTarget();
    assertEquals(tower.getTarget(), null);
  }
  @Test
  public void testAggroTargetAndScanTarget(){
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
    tower.startShooting();
    assertEquals(tower.countUnitsInList(),1);
    tower.startShooting();
    tower.startShooting();
    assertEquals(tower.countUnitsInList(),0);
    assertEquals(tower.getTarget(), null);
  }
  @Test
  public void testOutOfRange(){

    /*tower.initScan();

    tower.initScan();*/

    assertEquals(1, 1);
    /*tower.setMoney(20);
    tower.buildTower();
    int frostTower = tower.countFrostTowerTypes();
    assertEquals(frostTower,1);
    Tower frost = tower.getFrostTower();
    tower.getFrostTower().initScan();
    tower.getFrostTower().initScan();

    assertEquals(tower.getFrostTower().getTarget(), null)*/;

  }
  @Test
  public void testTicShooting(){
    Troop temp = tower.getTroopFromList(0);
    assertEquals(temp.isAlive(), true);
    tower.initScan();
    assertEquals(tower.checkIfUnitIsClose(temp),true);
    assertEquals(temp.isAlive(),true);
    tower.startShooting();
    assertEquals(temp.isAlive(),false);
    tower.startShooting();
    assertEquals(tower.getTarget(), null);
    tower.startShooting();

    assertEquals(tower.getTroopListSize(), 0);


    //assertEquals();


  }
  @Test
  public void ticMethod(){
    assertEquals(tower.getTroopListSize(),1);
    tower.tick();
    tower.tick();
    tower.tick();
    assertEquals(tower.getTroopListSize(),0);
  }


}
