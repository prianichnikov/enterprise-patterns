package util;

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
                connection = DriverManager.getConnection("jdbc:h2:mem:db1;USER=sa;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
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
        String personsTable = "create table persons (id int identity, " +
                "first_name varchar(255), last_name varchar(255), email_id int)";
        String emailsTable = "create table emails (id int identity, email varchar(255))";
        String personEmailForeignKey = "alter table persons add foreign key (email_id) references emails(id)";
        String emailsSequence = "create sequence seq_emails_id";
        statement.execute(personsTable);
        statement.execute(emailsTable);
        statement.execute(personEmailForeignKey);
        statement.execute(emailsSequence);
        statement.close();
    }
}
