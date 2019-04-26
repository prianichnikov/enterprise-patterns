import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NameFactoryTest {

    private final NameFactory nameFactory = new NameFactory();

    @Test
    void test_FirstFirst() {
        Assertions.assertTrue(nameFactory.getName("FirstFirst") instanceof FirstFirst);
    }

    @Test
    void test_LastFirst() {
        Assertions.assertTrue(nameFactory.getName("LastFirst") instanceof LastFirst);
    }

    @Test
    void test_not_LastFirst() {
        Assertions.assertFalse(nameFactory.getName("FirstFirst") instanceof LastFirst);
    }

    @Test
    void test_unknown_parameter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> nameFactory.getName("some class name"));
    }
}
