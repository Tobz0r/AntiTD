package AntiTD.database;

import java.sql.SQLException;

/**
 * Created by mattias on 2015-12-09.
 */
public class DatabaseHelper {
    public static boolean tableAlreadyExists(SQLException e) {
        if (e.getSQLState().equals("X0Y32")) {
            return true;
        }
        return false;
    }

    public static boolean postAlreadyExists(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return true;
        }
        return false;
    }
}
