import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonTableDataGatewayImpl implements PersonTableDataGateway {

    private final Connection connection;

    public PersonTableDataGatewayImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person findById(int id) throws SQLException {
        String sql = "select id, first_name, last_name, email from person where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.first()) {
            Person person = new Person();
            person.setId(rs.getInt("id"));
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            person.setEmail(rs.getString("email"));
            return person;
        } else {
            return null;
        }
    }

    @Override
    public Person findByEmail(String email) throws SQLException {
        String sql = "select id, first_name, last_name, email from person where email = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.first()) {
            Person person = new Person();
            person.setId(rs.getInt("id"));
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            person.setEmail(rs.getString("email"));
            return person;
        } else {
            return null;
        }
    }

    @Override
    public void add(int id, String firstName, String lastName, String email) throws SQLException {
        String sql = "insert into person (id, first_name, last_name, email) values (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, firstName);
        ps.setString(3, lastName);
        ps.setString(4, email);
        ps.execute();
    }

    @Override
    public void update(int id, String firstName, String lastName, String email) throws SQLException {
        String sql = "update person set first_name = ?, last_name = ?, email = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, email);
        ps.setInt(4, id);
        ps.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from person where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }
}
