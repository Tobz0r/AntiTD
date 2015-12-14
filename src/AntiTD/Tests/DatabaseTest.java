package AntiTD.Tests;

import AntiTD.database.DBModel;
import AntiTD.database.Database;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
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
        assertEquals(hs.getPlayername(), "LaVals");

    }

    @Test
    public void testGetHighscoreShouldReturnNull() throws Exception {
        DBModel hs = db.getHighscore("Laloa");
        assertNull(hs);

    }

    @Test
    public void testGetHighscores() throws Exception {
        db.printHighscores();
    }
}