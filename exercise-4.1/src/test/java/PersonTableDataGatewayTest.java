import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PersonTableDataGatewayTest {

    private static Connection connection;

    static {
        try {
            connection = DBUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final TableDataGateway<Person> personDataGateway = new PersonDataGatewayImpl(connection);

    @Test
    void test_FindNoExistsPerson() throws SQLException {
        Person person = new Person(1, "John", "Smith", "j.smith@mail.com");
        Person foundPerson = personDataGateway.findById(person.getId());
        Assertions.assertNull(foundPerson);
    }

    @Test
    void test_AddPerson() throws SQLException {
        Person person = new Person(2, "John", "Smith", "j.smith@mail.com");
        Person createdPerson = personDataGateway.add(person);
        Assertions.assertEquals(person, createdPerson);
    }

    @Test
    void test_UpdatePerson() throws SQLException {
        Person person = new Person(3, "John", "Smith", "j.smith@mail.com");
        Person createdPerson = personDataGateway.add(person);
        createdPerson.setFirstName("Bill");
        createdPerson.setLastName("Gates");
        createdPerson.setEmail("g.gates@gmail.com");
        Person updatedPerson = personDataGateway.update(createdPerson);
        Assertions.assertNotEquals(person, updatedPerson);
        Assertions.assertEquals(createdPerson, updatedPerson);
    }

    @Test
    void test_DeletePerson() throws SQLException {
        Person person = new Person(4, "John", "Smith", "j.smith@mail.com");
        Person createdPerson = personDataGateway.add(person);
        Assertions.assertEquals(person, createdPerson);
        personDataGateway.delete(createdPerson);
        Person nullPerson = personDataGateway.findById(createdPerson.getId());
        Assertions.assertNull(nullPerson);
    }
}
