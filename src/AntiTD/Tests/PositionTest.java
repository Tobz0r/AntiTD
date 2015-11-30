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

    Position p;

    @Before
    public void setUp() throws Exception {
        p = new Position(1, 2);
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
}