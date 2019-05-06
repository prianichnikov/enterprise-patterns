package mapper;

import java.util.Optional;

public interface Mapper<T> {

    Optional<T> find(int id);

    void add(T obj);

    void update(T obj);

    void delete(T obj);
}
