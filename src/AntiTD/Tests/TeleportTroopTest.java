package AntiTD.Tests;

import AntiTD.Position;
import AntiTD.tiles.GoalTile;
import AntiTD.tiles.PathTile;
import AntiTD.tiles.StartTile;
import AntiTD.tiles.Tile;
import AntiTD.troops.BasicTroop;
import AntiTD.troops.TeleportTroop;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mattias on 2015-12-07.
 */
public class TeleportTroopTest {

    Tile start;
    Tile middle1;
    Tile middle2;
    Tile middle3;
    Tile end;

    @Before
    public void setUp() throws Exception {
        //setup tiles
        start = new StartTile(
                new Position(0, 0)
        );
        middle1 = new PathTile(
                new Position(0, 1)
        );
        middle2 = new PathTile(
                new Position(0, 2)
        );
        middle3 = new PathTile(
                new Position(0, 3)
        );
        end = new GoalTile(
                new Position(0, 4)
        );
        //setup teleport
        //middle1.setTeleportTo(middle3);

        //setup neighbors
        start.setNeighbors(new Tile[]{
                middle1
        });
        middle1.setNeighbors(new Tile[]{
                start, middle2
        });
        middle2.setNeighbors(new Tile[]{
                middle1, middle3
        });
        middle3.setNeighbors(new Tile[]{
                middle3, end
        });
        end.setNeighbors(new Tile[]{
                middle3
        });
    }

    @Test
    public void testTeleportShouldGetToGoalIn2Moves() throws Exception {
        TeleportTroop t = new TeleportTroop(null, start, 1, 1, 100, null);
        middle1.setTeleportTo(middle3);
        t.tick();
        t.tick();
        assertEquals(end, t.getTilePosition());
    }

    @Test
    public void testInitTeleportShouldReturnTrue() throws Exception {
        TeleportTroop t = new TeleportTroop(null, start,1, 1, 100, new Integer(1));
        t.tick();
        assertEquals(middle1, t.getTilePosition());
        t.initTeleport();
        t.tick();
        assertEquals(middle2, t.getTilePosition());
        t.tick();
        assertEquals(middle3, t.getTilePosition());
        t.tick();
        assertEquals(end, t.getTilePosition());
        //assertEquals(true, middle1.isTeleporter());
    }

    @Test
    public void testCreateTeleportAndMoveBasicShouldReturnTrue() throws Exception {

        TeleportTroop t = new TeleportTroop(null, start, 1, 1, 100, new Integer(2));
        BasicTroop basicT = new BasicTroop(null, start, 1, 1, 100);

        t.tick();
        assertEquals(middle1, t.getTilePosition());

        t.initTeleport();
        t.tick();
        assertEquals(middle2, t.getTilePosition());

        t.tick();
        assertEquals(middle3, t.getTilePosition());

        t.tick();
        assertEquals(end, t.getTilePosition());

        basicT.tick();
        assertEquals(middle1, basicT.getTilePosition());

        basicT.tick();
        assertEquals(end, basicT.getTilePosition());
        //assertEquals(true, basicT.hasReachedGoal());

    }

}