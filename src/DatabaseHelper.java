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
}
