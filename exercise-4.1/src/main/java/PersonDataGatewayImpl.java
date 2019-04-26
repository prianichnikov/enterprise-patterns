import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDataGatewayImpl implements TableDataGateway<Person> {

    private final Connection connection;

    public PersonDataGatewayImpl(Connection connection) {
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
    public Person add(Person person) throws SQLException {
        String sql = "insert into person (id, first_name, last_name, email) values (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, person.getId());
        ps.setString(2, person.getFirstName());
        ps.setString(3, person.getLastName());
        ps.setString(4, person.getEmail());
        ps.execute();
        return findById(person.getId());
    }

    @Override
    public Person update(Person person) throws SQLException {
        String sql = "update person set first_name = ?, last_name = ?, email = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, person.getFirstName());
        ps.setString(2, person.getLastName());
        ps.setString(3, person.getEmail());
        ps.setInt(4, person.getId());
        ps.execute();
        return findById(person.getId());
    }

    @Override
    public void delete(Person person) throws SQLException {
        String sql = "delete from person where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, person.getId());
        ps.execute();
    }
}
