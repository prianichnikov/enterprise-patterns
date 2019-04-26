package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static Connection connection;

    private DBUtil() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:h2:mem:");
            connection.setAutoCommit(true);
            prepareDatabase(connection);
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

    private static void prepareDatabase(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.addBatch(
                    "create table products (" +
                    "id int primary key, name varchar(255), type char(1))");
            statement.addBatch("create table contracts(" +
                    "id int primary key,product int,revenue decimal(18,2),dateSigned date)");
            statement.addBatch("create table revenueRecognitions(" +
                    "contract int,amount decimal(18,2),recognizedOn date," +
                    "constraint revenuerec_pk primary key (contract, recognizedOn))");
            statement.addBatch("insert into products values " +
                    "(1, 'Word processor', 'W')," +
                    "(2, 'Spreadsheet', 'S')," +
                    "(3, 'Database', 'D')");
            statement.addBatch("insert into contracts values " +
                    "(1, 3, 300.00, date '2019-03-01')," +
                    "(2, 1, 240.00, date '2019-03-01')," +
                    "(3, 2, 150.00, date '2019-03-01')");
            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println("Cannot prepare database");
            e.printStackTrace();
        }
    }
}
