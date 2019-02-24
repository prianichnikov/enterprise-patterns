package exercise2.tablemodel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {

    private int id;
    private String name;
    private String type;

    public Product() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Product getProductFromResultSet(ResultSet resultSet) {
        Product product = null;
        try {
            if (resultSet.first()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setType(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
