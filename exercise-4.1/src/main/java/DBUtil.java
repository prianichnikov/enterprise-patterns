import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static Connection connection;

    private DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:h2:mem:");
            connection.setAutoCommit(true);
            loadSchema(connection);
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

    private static void loadSchema(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String schema = "create table person (" +
                            "id int identity, " +
                            "first_name varchar(255), " +
                            "last_name varchar(255), " +
                            "email varchar(255))";
            statement.execute(schema);
            statement.close();
        } catch (SQLException e) {
            System.err.println("Cannot prepare database");
            e.printStackTrace();
        }
    }
}
