package mapper;

import domain.Person;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PersonMapperImpl implements Mapper<Person> {

    private static final Connection CONNECTION = DBUtil.getConnection();

    @Override
    public Optional<Person> find(int id) {
        String selectPerson = "select id, first_name, last_name, email_id from persons where id = ?";
        String selectEmail = "select id, email from emails where id = ?";

        Person person = null;
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(selectPerson);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                person = new Person();
                person.setId(id);
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                int emailId = rs.getInt("email_id");

                ps = CONNECTION.prepareStatement(selectEmail);
                ps.setInt(1, emailId);
                rs = ps.executeQuery();
                if (rs.first()) {
                    person.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(person);
    }

    @Override
    public void add(Person person) {
        String insertPerson = "insert into persons(id, first_name, last_name, email_id) values (?,?,?,?)";
        String insertEmail = "insert into emails(id, email) values (?, ?)";
        String emailSequence = "seq_emails_id";
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(insertEmail);
            int emailId = getIdFromSequence(emailSequence);
            ps.setInt(1, emailId);
            ps.setString(2, person.getEmail());
            ps.execute();

            ps = CONNECTION.prepareStatement(insertPerson);
            ps.setInt(1, person.getId());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getLastName());
            ps.setInt(4, emailId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Person person) {
        String selectPerson = "select id, first_name, last_name, email_id from persons where id = ?";
        String updatePerson = "update persons set first_name = ?, last_name = ? where id = ?";
        String updateEmail = "update emails set email = ? where id = ?";
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(selectPerson);
            ps.setInt(1, person.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                int emailId = rs.getInt("email_id");

                ps = CONNECTION.prepareStatement(updateEmail);
                ps.setString(1, person.getEmail());
                ps.setInt(2, emailId);
                ps.executeUpdate();

                ps = CONNECTION.prepareStatement(updatePerson);
                ps.setString(1, person.getFirstName());
                ps.setString(2, person.getLastName());
                ps.setInt(3, person.getId());
                ps.executeUpdate();
            } else {
                throw new PersonNotFoundException(String.format("person with id %d not found", person.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Person person) {
        String selectPerson = "select id, first_name, last_name, email_id from persons where id = ?";
        String deletePerson = "delete from persons where id = ?";
        String deleteEmail = "delete from emails where id = ?";
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(selectPerson);
            ps.setInt(1, person.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                int emailId = rs.getInt("email_id");

                ps = CONNECTION.prepareStatement(deletePerson);
                ps.setInt(1, person.getId());
                ps.executeUpdate();

                if (emailId != 0) {
                    ps = CONNECTION.prepareStatement(deleteEmail);
                    ps.setInt(1, emailId);
                    ps.executeUpdate();
                }
            } else {
                throw new PersonNotFoundException(String.format("person with id %d not found", person.getId()));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getIdFromSequence(String sequenceName) {
        int id = -1;
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(String.format("select %s.nextval from dual", sequenceName));
            ResultSet rs = ps.executeQuery();
            if(rs.first()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
