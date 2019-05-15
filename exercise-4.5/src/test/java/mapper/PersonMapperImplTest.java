package mapper;

import domain.Person;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperImplTest {

    private final Mapper<Person> personMapper = new PersonMapperImpl();

    @Test
    void add() {
        int id = 1;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        Person person = new Person(id, firstName, lastName, email);
        personMapper.add(person);
        Optional<Person> personOptional = personMapper.find(id);
        assertTrue(personOptional.isPresent());

        person = personOptional.get();
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(email, person.getEmail());
    }

    @Test
    void update() {
        int id = 2;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        String updFirstName = "John";
        String updLastName = "Snow";
        String updEmail = "j.snow@knownothing.com";
        Person person = new Person(id, firstName, lastName, email);
        personMapper.add(person);
        Optional<Person> personOptional = personMapper.find(id);
        assertTrue(personOptional.isPresent());

        person = personOptional.get();
        person.setFirstName(updFirstName);
        person.setLastName(updLastName);
        person.setEmail(updEmail);
        personMapper.update(person);
        personOptional = personMapper.find(id);
        assertTrue(personOptional.isPresent());

        person = personOptional.get();
        assertEquals(updFirstName, person.getFirstName());
        assertEquals(updLastName, person.getLastName());
        assertEquals(updEmail, person.getEmail());
    }

    @Test
    void delete() {
        int id = 3;
        String firstName = "John";
        String lastName = "Deere";
        String email = "j.deere@mail.com";
        Person person = new Person(id, firstName, lastName, email);
        personMapper.add(person);
        Optional<Person> personOptional = personMapper.find(id);
        assertTrue(personOptional.isPresent());

        personMapper.delete(person);
        personOptional = personMapper.find(id);
        assertFalse(personOptional.isPresent());
    }
}