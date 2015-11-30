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

    BasicTroop t;
    Tile start;
    Tile middle;
    Tile end;

    @Before
    public void setUp() throws Exception {
        start = new StartTile();
        middle = new PathTile();
        end = new GoalTile();

        start.setNeighbors(new Tile[]{middle});
        middle.setNeighbors(new Tile[]{start,end});
        end.setNeighbors(new Tile[]{middle});
    }

    @Test
    public void testTick() throws Exception {
        Troop t = new BasicTroop(start);
        t.tick();
        assertEquals(t.getPosition(), middle);
    }

    @Test
    public void testTick1() throws Exception {
        Troop t = new BasicTroop(start);
        t.tick();
        t.tick();
        assertEquals(t.getPosition(), end);
    }

    @Test
    public void testGetImage() throws Exception {
        //TODO: testGetImage
    }

    @Test
    public void testGetCurrentScore() throws Exception {
        Troop t = new BasicTroop(start);
    }

    @Test
    public void testAttackThis() throws Exception {

    }

    @Test
    public void testIsAlive() throws Exception {

    }

    @Test
    public void testGetPosition() throws Exception {

    }
}