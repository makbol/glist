package pl.edu.agh.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Klasa obslugujaca polaczenie z baza danych
 */
public final class DBManager {

    private static final String ADD_USER = "insert into users(name, password) values (?, ?)";

    private static final String LOG_USER = "select id from users where name=? and password=?";

    private static Logger LOGGER = LogManager.getLogger(DBManager.class);

    private static Connection conn = null;

    static {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:tron", "SA", "");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Error while initializing db connection!", e);
        }
    }

    /**
     * Dodanie uzytkownika na bazie.
     *
     * @return zwraca 1 jesli dodano uzytkownika, 0 jesli operacja sie nie powiodla
     */
    public static int registerUser(final String username, final String password) {
        String methodName = "registerUser";
        try(PreparedStatement statement = conn.prepareStatement(ADD_USER)) {
            statement.setString(1, username);
            statement.setString(2, password);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(methodName, e);
        }
        return 0;
    }

    /**
     * Logowanie uzytkownika na bazie, ze sprawdzaniem hasla
     *
     * @return zwraca id uzytkownika jesli udalo sie zalogowac, -1 w przypadku gdy sie nie udalo
     */
    public static int loginUser(String name, String password) {
        String methodName = "loginUser";
        try(PreparedStatement statement = conn.prepareStatement(LOG_USER)) {
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(methodName, e);
        }
        return -1;
    }
}
