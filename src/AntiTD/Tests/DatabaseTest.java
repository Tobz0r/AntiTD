package AntiTD.Tests;

import AntiTD.database.DBModel;
import AntiTD.database.Database;
import AntiTD.database.DatabaseEntryDoesNotExists;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import static org.junit.Assert.*;

/**
 * Created by id12men on 2015-12-14.
 */
public class DatabaseTest {
    Database db;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.insertOrUpdateHighscore("DatabaseTestUser1", 1);
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void testInsertOrUpdateScore10ShouldReturn10() throws Exception {
        db.insertOrUpdateHighscore("Test3", 10);
        DBModel usr = db.getHighscore("Test3");
        assertEquals(10, usr.getScore());
    }

    @Test
    public void testGetHighscoreShouldReturnTrue() throws Exception {
        DBModel hs = db.getHighscore("LaVals");
        assertEquals("LaVals", hs.getPlayername());

    }

    @Test
    public void testGetHighscoreShouldTrowExceptionReturnNull() throws Exception {
        DBModel usr = null;
        try {
            usr = db.getHighscore("Laloa");
        } catch (DatabaseEntryDoesNotExists e) {
            assertNull(usr);
        }

    }

    @Test
    public void testGetHighscoresShouldContainAtLeastOneReturnTrue() throws Exception {
        ArrayList<DBModel> entrys = db.getHighscores();
        db.printHighscores();
        assertTrue(entrys.size() > 0);
    }
}