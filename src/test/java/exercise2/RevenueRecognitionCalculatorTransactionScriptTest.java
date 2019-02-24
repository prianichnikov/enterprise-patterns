package exercise2;

import exercise2.transactionscript.RevenueRecognitionTransactionScript;
import exercise2.util.DBUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;

public class RevenueRecognitionCalculatorTransactionScriptTest {

    private static Connection connection;

    static {
        try {
            connection = DBUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private RevenueRecognitionTransactionScript revenueRecognition =
            new RevenueRecognitionTransactionScript(connection);

    @Test
    void test_WordProcessorRevenueRecognitionAmount() throws SQLException {
        revenueRecognition.calculateByContractId(2);
        BigDecimal amountSum = calculateAmountSum("W");
        BigDecimal contractAmount = getContractRevenue("W");
        Assertions.assertEquals(amountSum, contractAmount);
    }

    @Test
    void test_DatabaseRevenueRecognitionAmount() throws SQLException {
        revenueRecognition.calculateByContractId(1);
        BigDecimal amountSum = calculateAmountSum("D");
        BigDecimal contractAmount = getContractRevenue("D");
        Assertions.assertEquals(amountSum, contractAmount);
    }

    @Test
    void test_SpreadsheetRevenueRecognitionAmount() throws SQLException {
        revenueRecognition.calculateByContractId(3);
        BigDecimal amountSum = calculateAmountSum("S");
        BigDecimal contractAmount = getContractRevenue("S");
        Assertions.assertEquals(amountSum, contractAmount);

    }

    private BigDecimal calculateAmountSum(String productType) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select r.contract, r.amount, r.recognizedon " +
                "from revenueRecognitions r " +
                "join contracts c on c.id = r.contract " +
                "join products p on p.id = c.product " +
                "where p.type = ?");
        preparedStatement.setString(1, productType);
        ResultSet resultSet = preparedStatement.executeQuery();
        BigDecimal amountSum = BigDecimal.ZERO;
        while (resultSet.next()) {
            amountSum = amountSum.add(resultSet.getBigDecimal("amount"));
        }
        return amountSum;
    }

    private BigDecimal getContractRevenue(String productType) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select c.id, c.product, c.revenue, c.datesigned " +
                "from contracts c " +
                "join products p on p.id = c.product " +
                "where p.type = ?");
        preparedStatement.setString(1, productType);
        ResultSet resultSet = preparedStatement.executeQuery();
        BigDecimal contractAmount = BigDecimal.ONE;
        if (resultSet.first()) {
            contractAmount = resultSet.getBigDecimal("revenue");
        }
        return contractAmount;
    }

    @AfterAll
    static void closeConnection() {
        DBUtil.closeConnection();
    }
}
