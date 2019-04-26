package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contract {

    private int id;
    private int product;
    private BigDecimal revenue;
    private Date dateSigned;

    public Contract() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public Date getDateSigned() {
        return dateSigned;
    }

    public void setDateSigned(Date dateSigned) {
        this.dateSigned = dateSigned;
    }

    public static Contract getContractFromResultSet(ResultSet resultSet) {
        Contract contract = null;
        try {
            if (resultSet.first()) {
                contract = new Contract();
                contract.setId(resultSet.getInt("id"));
                contract.setProduct(resultSet.getInt("product"));
                contract.setRevenue(resultSet.getBigDecimal("revenue"));
                contract.setDateSigned(resultSet.getDate("datesigned"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contract;
    }
}
