import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PersonPersonTableDataGatewayTest {

    private static Connection connection;

    static {
        try {
            connection = DBUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final PersonTableDataGateway personDataGateway = new PersonTableDataGatewayImpl(connection);

    @Test
    void test_FindNoExistsPersonById() throws SQLException {
        Person person = new Person(1, "John", "Smith", "j.smith1@mail.com");
        Person foundPerson = personDataGateway.findById(person.getId());
        Assertions.assertNull(foundPerson);
    }

    @Test
    void test_FindNoExistsPersonByEmail() throws SQLException {
        Person person = new Person(2, "John", "Smith", "j.smith2@mail.com");
        Person foundPerson = personDataGateway.findByEmail(person.getEmail());
        Assertions.assertNull(foundPerson);
    }

    @Test
    void test_AddPerson() throws SQLException {
        int id = 3;
        String firstName = "John";
        String lastName = "Smith";
        String email = "j.smith3@mail.com";
        Person person = new Person(id, firstName, lastName, email);
        personDataGateway.add(id, firstName, lastName, email);
        Person createdPerson = personDataGateway.findById(id);
        Assertions.assertEquals(person, createdPerson);
    }

    @Test
    void test_UpdatePerson() throws SQLException {
        int id = 4;
        String firstName = "John";
        String lastName = "Smith";
        String email = "j.smith4@mail.com";
        Person person = new Person(id, firstName, lastName, email);
        personDataGateway.add(id, firstName, lastName, email);
        Person createdPerson = personDataGateway.findById(id);
        Assertions.assertEquals(person, createdPerson);

        String updatedFirstName = "Bill";
        String updatedLastName = "Gates";
        String updatedEmail = "b.gates@gmail.com";
        personDataGateway.update(id, updatedFirstName, updatedLastName, updatedEmail);
        Person updatedPerson = personDataGateway.findById(id);
        Assertions.assertNotEquals(createdPerson.getFirstName(), updatedPerson.getFirstName());
        Assertions.assertNotEquals(createdPerson.getLastName(), updatedPerson.getLastName());
        Assertions.assertNotEquals(createdPerson.getEmail(), updatedPerson.getEmail());
    }

    @Test
    void test_DeletePerson() throws SQLException {
        int id = 5;
        String firstName = "John";
        String lastName = "Smith";
        String email = "j.smith5@mail.com";
        Person person = new Person(id, firstName, lastName, email);
        personDataGateway.add(id, firstName, lastName, email);
        Person createdPerson = personDataGateway.findById(id);
        Assertions.assertEquals(person, createdPerson);
        personDataGateway.delete(id);
        Person nullPerson = personDataGateway.findById(id);
        Assertions.assertNull(nullPerson);
    }
}
