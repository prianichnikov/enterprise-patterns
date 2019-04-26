import java.sql.SQLException;

public interface TableDataGateway<T> {

    T findById(int id) throws SQLException;

    T add(T object) throws SQLException;

    T update(T object) throws SQLException;

    void delete(T object) throws SQLException;

}
