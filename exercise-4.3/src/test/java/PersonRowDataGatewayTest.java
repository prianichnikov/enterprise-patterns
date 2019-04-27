import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PersonRowDataGatewayTest {

    @Test
    void create() {
        int id = 1;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        PersonRowDataGateway newPerson = new PersonRowDataGateway(id, firstName, lastName, email);
        newPerson.create();
        PersonRowDataGateway foundPerson = PersonRowDataGateway.findById(id);
        Assertions.assertEquals(newPerson, foundPerson);
    }

    @Test
    void findById() {
        int id = 2;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        PersonRowDataGateway newPerson = new PersonRowDataGateway(id, firstName, lastName, email);
        newPerson.create();
        PersonRowDataGateway foundPerson = PersonRowDataGateway.findById(id);
        Assertions.assertEquals(newPerson, foundPerson);
    }

    @Test
    void update() {
        int id = 3;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        PersonRowDataGateway newPerson = new PersonRowDataGateway(id, firstName, lastName, email);
        newPerson.create();
        PersonRowDataGateway foundPerson = PersonRowDataGateway.findById(id);
        Assertions.assertEquals(newPerson, foundPerson);

        String updatedFirstName = "Bill";
        String updatedLastName = "Gates";
        String updatedEmail = "b.gates@gmail.com";
        foundPerson.setFirstName(updatedFirstName);
        foundPerson.setLastName(updatedLastName);
        foundPerson.setEmail(updatedEmail);
        foundPerson.update();

        foundPerson = PersonRowDataGateway.findById(id);
        Assertions.assertEquals(foundPerson.getFirstName(), updatedFirstName);
        Assertions.assertEquals(foundPerson.getLastName(), updatedLastName);
        Assertions.assertEquals(foundPerson.getEmail(), updatedEmail);
    }

    @Test
    void delete() {
        int id = 4;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        PersonRowDataGateway newPerson = new PersonRowDataGateway(id, firstName, lastName, email);
        newPerson.create();
        PersonRowDataGateway foundPerson = PersonRowDataGateway.findById(id);
        Assertions.assertEquals(newPerson, foundPerson);

        foundPerson.delete();
        foundPerson = PersonRowDataGateway.findById(id);
        Assertions.assertNull(foundPerson);
    }
}