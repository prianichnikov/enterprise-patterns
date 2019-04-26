package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RevenueRecognitions {

    private int contract;
    private BigDecimal amount;
    private Date recognizedOn;

    public RevenueRecognitions() { }

    public RevenueRecognitions(int contract, BigDecimal amount, Date recognizedOn) {
        this.contract = contract;
        this.amount = amount;
        this.recognizedOn = recognizedOn;
    }

    public int getContract() {
        return contract;
    }

    public void setContract(int contract) {
        this.contract = contract;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getRecognizedOn() {
        return recognizedOn;
    }

    public void setRecognizedOn(Date recognizedOn) {
        this.recognizedOn = recognizedOn;
    }

    public static RevenueRecognitions getRevenueRecognitionsFromResultSet(ResultSet resultSet) {
        RevenueRecognitions revenueRecognitions = null;
        try {
            revenueRecognitions = new RevenueRecognitions();
            revenueRecognitions.setContract(resultSet.getInt("contract"));
            revenueRecognitions.setAmount(resultSet.getBigDecimal("amount"));
            revenueRecognitions.setRecognizedOn(resultSet.getDate("recognizedon"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueRecognitions;
    }
}
