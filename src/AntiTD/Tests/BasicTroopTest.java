package AntiTD.Tests;

import AntiTD.Position;
import AntiTD.tiles.*;
import AntiTD.troops.BasicTroop;
import AntiTD.troops.Troop;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by mattias on 2015-11-30.
 */
public class BasicTroopTest {
    Tile start;
    Tile middle;
    Tile end;

    @Before
    public void setUp() throws Exception {
        start = new StartTile(new Position(0, 0));
        middle = new PathTile(new Position(0, 1));
        end = new GoalTile(new Position(0, 2));

        start.setNeighbors(new Tile[]{middle});
        middle.setNeighbors(new Tile[]{start, end});
        end.setNeighbors(new Tile[]{middle});
    }

    @Test
    public void testTickOnceShouldReturnMiddle() throws Exception {
        Troop t = new BasicTroop(start);
        t.tick();
        assertEquals(t.getTilePosition(), middle);
    }

    @Test
    public void testTickTwiceShouldReturnEnd() throws Exception {
        Troop t = new BasicTroop(start);
        t.tick();
        t.tick();
        assertEquals(t.getTilePosition(), end);
    }

    @Test
    public void testGetCurrentScoreShouldReturn10() throws Exception {
        Troop t = new BasicTroop(start);
        t.tick();
        t.tick();
        assertEquals(t.getCurrentScore(),10);
    }

    @Test
    public void testAttackThis1DamageShouldDecrease1() throws Exception {
        Troop t = new BasicTroop(start);
        int healthBefore = t.getHealth();
        t.attackThis(1);
        assertEquals(t.getHealth(), healthBefore-1);
    }

    @Test
    public void testDeal10DamageShouldDie() throws Exception {
        Troop t = new BasicTroop(start);
        t.attackThis(10);
        assertEquals(t.isAlive(), false);
    }

    @Test
    public void testGetPositionShouldReturnStart() throws Exception {
        Troop t = new BasicTroop(start);
        assertEquals(t.getTilePosition(), start);
    }
}