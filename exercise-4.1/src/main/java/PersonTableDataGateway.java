import java.sql.SQLException;

public interface PersonTableDataGateway {

    Person findById(int id) throws SQLException;

    Person findByEmail(String email) throws SQLException;

    void add(int id, String firstName, String lastName, String email) throws SQLException;

    void update(int id, String firstName, String lastName, String email) throws SQLException;

    void delete(int id) throws SQLException;

}
