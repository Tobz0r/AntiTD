package AntiTD.Tests;

import AntiTD.database.DBModel;
import AntiTD.database.Database;
import AntiTD.database.DatabaseEntryDoesNotExistsException;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

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
    public void testGetHighscoreNotExistingShouldThrowException() throws Exception {
        try {
            DBModel hs = db.getHighscore("LaVals");
            fail("Exception not thrown");
        } catch (Exception e) {
            assertEquals(DatabaseEntryDoesNotExistsException.class, e.getClass());
        }

    }

    @Test
    public void testGetHighscoreShouldreturn10() throws Exception {
        DBModel usr = null;
        try {
            usr = db.getHighscore("Test3");
            assertEquals(10, usr.getScore());
        } catch (DatabaseEntryDoesNotExistsException e) {
            fail("Element dos not exist");
        }

    }

    @Test
    public void testGetHighscoresShouldContainAtLeastOneReturnTrue() throws Exception {
        ArrayList<DBModel> entrys = db.getHighscores();
        db.printHighscores();
        assertTrue(entrys.size() > 0);
    }
}