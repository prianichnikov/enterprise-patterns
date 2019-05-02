import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static Connection connection;

    private DBUtil() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:h2:mem:");
                connection.setAutoCommit(true);
                loadSchema(connection);
            } catch (SQLException ex) {
                throw new RuntimeException("Cannot load and prepare database");
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadSchema(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String schema = "create table person (" +
                        "id int identity, " +
                        "first_name varchar(255), " +
                        "last_name varchar(255), " +
                        "email varchar(255))";
        statement.execute(schema);
        statement.close();
    }
}
