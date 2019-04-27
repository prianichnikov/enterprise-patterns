import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class PersonActiveRecord {

    private static final Connection CONNECTION = DBUtil.getConnection();

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private PersonActiveRecord() { }

    public PersonActiveRecord(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonActiveRecord that = (PersonActiveRecord) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    public static Optional<PersonActiveRecord> findById(int id) {
        String sql = "select id, first_name, last_name, email from person where id = ?";
        PersonActiveRecord person = null;
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                person = new PersonActiveRecord();
                person.setId(id);
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(person);
    }

    public void create() {
        String sql = "insert into person(id, first_name, last_name, email) values (?, ?, ?, ?)";
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setString(2, this.firstName);
            ps.setString(3, this.lastName);
            ps.setString(4, this.email);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        String sql = "update person set first_name= ?, last_name = ?, email = ? where id = ?";
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(sql);
            ps.setString(1, this.firstName);
            ps.setString(2, this.lastName);
            ps.setString(3, this.email);
            ps.setInt(4, this.id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "delete from person where id = ?";
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isEmailValid() {
        if (this.email == null || this.email.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$",
                Pattern.CASE_INSENSITIVE);
        return pattern.matcher(this.email).find();
    }

}
