import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class PersonActiveRecordTest {

    @Test
    void create() {
        int id = 1;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        PersonActiveRecord newPerson = new PersonActiveRecord(id, firstName, lastName, email);
        newPerson.create();
        Optional<PersonActiveRecord> foundPerson = PersonActiveRecord.findById(id);
        Assertions.assertTrue(foundPerson.isPresent());
        Assertions.assertEquals(newPerson, foundPerson.get());
    }

    @Test
    void findById() {
        int id = 2;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        PersonActiveRecord newPerson = new PersonActiveRecord(id, firstName, lastName, email);
        newPerson.create();
        Optional<PersonActiveRecord> foundPerson = PersonActiveRecord.findById(id);
        Assertions.assertTrue(foundPerson.isPresent());
        Assertions.assertEquals(newPerson, foundPerson.get());
    }

    @Test
    void update() {
        int id = 3;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        PersonActiveRecord newPerson = new PersonActiveRecord(id, firstName, lastName, email);
        newPerson.create();
        Optional<PersonActiveRecord> optionalFoundPerson = PersonActiveRecord.findById(id);
        Assertions.assertTrue(optionalFoundPerson.isPresent());
        Assertions.assertEquals(newPerson, optionalFoundPerson.get());

        String updatedFirstName = "Bill";
        String updatedLastName = "Gates";
        String updatedEmail = "b.gates@gmail.com";
        PersonActiveRecord foundPerson = optionalFoundPerson.get();
        foundPerson.setFirstName(updatedFirstName);
        foundPerson.setLastName(updatedLastName);
        foundPerson.setEmail(updatedEmail);
        foundPerson.update();

        optionalFoundPerson = PersonActiveRecord.findById(id);
        Assertions.assertTrue(optionalFoundPerson.isPresent());
        foundPerson = optionalFoundPerson.get();
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
        PersonActiveRecord newPerson = new PersonActiveRecord(id, firstName, lastName, email);
        newPerson.create();
        Optional<PersonActiveRecord> foundPerson = PersonActiveRecord.findById(id);
        Assertions.assertTrue(foundPerson.isPresent());
        Assertions.assertEquals(newPerson, foundPerson.get());

        foundPerson.get().delete();
        foundPerson = PersonActiveRecord.findById(id);
        Assertions.assertFalse(foundPerson.isPresent());
    }

    @Test
    void isEmailValid() {
        int id = 5;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mailcom";
        PersonActiveRecord newPerson = new PersonActiveRecord(id, firstName, lastName, email);
        Assertions.assertFalse(newPerson.isEmailValid());

        newPerson.setEmail("j.deere@mail.com");
        Assertions.assertTrue(newPerson.isEmailValid());
    }
}