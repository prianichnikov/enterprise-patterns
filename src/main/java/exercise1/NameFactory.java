package exercise1;

public class NameFactory {

    public NameFactory() {
    }

    public Namer getName(String name) {
        if (name.equals(FirstFirst.class.getSimpleName())) {
            return new FirstFirst(name);
        } else if (name.equals(LastFirst.class.getSimpleName())) {
            return new LastFirst(name);
        }
        throw new IllegalArgumentException("unknown class name");
    }

}
