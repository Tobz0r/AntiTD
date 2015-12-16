package AntiTD.Tests;

import AntiTD.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mattias on 2015-11-30.
 */
public class PositionTest {

    Position p,pnorth,psouth,peast,pwest;

    @Before
    public void setUp() throws Exception {
        p = new Position(1, 2);
        pnorth = new Position(1,1);
        psouth = new Position(1,3);
        pwest = new Position(0,2);
        peast = new Position(2,2);
    }

    @After
    public void tearDown() throws Exception {
        p = null;
    }

    @Test
    public void testGetX() throws Exception {
        assertEquals(p.getX(), 1);
    }

    @Test
    public void testGetY() throws Exception {
        assertEquals(p.getY(), 2);
    }
    @Test
    public void testIsPosToNorth() throws Exception{
        assertTrue(p.IsPosToNorth(pnorth));
    }
    @Test
    public void testIsPosToSouth() throws Exception{
        assertTrue(p.IsPosToSouth(psouth));
    }
    @Test
    public void testIsPosToWest() throws Exception{
        assertTrue(p.IsPosToWest(pwest));
    }
    @Test
    public void testIsPosToEast() throws Exception{
        assertTrue(p.IsPosToEast(peast));
    }
}