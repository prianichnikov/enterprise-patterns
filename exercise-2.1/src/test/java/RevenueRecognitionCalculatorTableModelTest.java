import tablemodel.*;
import util.DBUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;

public class RevenueRecognitionCalculatorTableModelTest {

    private static Connection connection;

    static {
        try {
            connection = DBUtil.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static final RevenueRecognitionCalculator revenueRecognitionCalculator =
            new RevenueRecognitionCalculator(connection);

    @BeforeAll
    static void runCalculations() {
        revenueRecognitionCalculator.calculateByContractId(1);
        revenueRecognitionCalculator.calculateByContractId(2);
        revenueRecognitionCalculator.calculateByContractId(3);
    }

    @Test
    void test_WordProcessorRevenueRecognitionsSum() throws SQLException {
        int contractId = 2;
        Contract contract = getContract(contractId);
        BigDecimal sum = getRevenueRecognitionsSum(contractId);
        Assertions.assertEquals(sum, contract.getRevenue());
    }

    @Test
    void test_WordProcessorRevenueRecognitionsAmount() throws SQLException {
        int contractId = 2;
        Contract contract = getContract(contractId);
        Product product = getProduct(contract.getProduct());
        int amount = RevenueRecognitionsFabric.createRevenueRecognitions(contract, product).size();
        int revenueRecognitionsAmount = getRevenueRecognitionsAmount(contractId);
        Assertions.assertEquals(revenueRecognitionsAmount, amount);
    }

    @Test
    void test_SpreadSheetRevenueRecognitionsSum() throws SQLException {
        int contractId = 3;
        Contract contract = getContract(contractId);
        BigDecimal sum = getRevenueRecognitionsSum(contractId);
        Assertions.assertEquals(sum, contract.getRevenue());
    }

    @Test
    void test_SpreadsheetRevenueRecognitionsAmount() throws SQLException {
        int contractId = 3;
        Contract contract = getContract(contractId);
        Product product = getProduct(contract.getProduct());
        int amount = RevenueRecognitionsFabric.createRevenueRecognitions(contract, product).size();
        int revenueRecognitionsAmount = getRevenueRecognitionsAmount(contractId);
        Assertions.assertEquals(revenueRecognitionsAmount, amount);
    }

    @Test
    void test_DatabaseRevenueRecognitionsSum() throws SQLException {
        int contractId = 1;
        Contract contract = getContract(contractId);
        BigDecimal sum = getRevenueRecognitionsSum(contractId);
        Assertions.assertEquals(sum, contract.getRevenue());
    }

    @Test
    void test_DatabaseRevenueRecognitionsAmount() throws SQLException {
        int contractId = 1;
        Contract contract = getContract(contractId);
        Product product = getProduct(contract.getProduct());
        int amount = RevenueRecognitionsFabric.createRevenueRecognitions(contract, product).size();
        int revenueRecognitionsAmount = getRevenueRecognitionsAmount(contractId);
        Assertions.assertEquals(revenueRecognitionsAmount, amount);
    }

    private Contract getContract(int contractId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select id,product,revenue,datesigned from contracts where id = ?");
        preparedStatement.setInt(1, contractId);
        return Contract.getContractFromResultSet(preparedStatement.executeQuery());
    }

    private Product getProduct(int productId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select id,name,type from products where id = ?");
        preparedStatement.setInt(1, productId);
        return Product.getProductFromResultSet((preparedStatement.executeQuery()));
    }

    private BigDecimal getRevenueRecognitionsSum(int contractId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select contract,amount,recognizedon from revenueRecognitions where contract = ?");
        preparedStatement.setInt(1, contractId);
        ResultSet resultSet = preparedStatement.executeQuery();
        BigDecimal sum = BigDecimal.ZERO;
        while (resultSet.next()) {
            sum = sum.add(RevenueRecognitions.getRevenueRecognitionsFromResultSet(resultSet).getAmount());
        }
        return sum;
    }

    private int getRevenueRecognitionsAmount(int contractId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select count(*) from revenueRecognitions where contract = ?");
        preparedStatement.setInt(1, contractId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.first();
        return resultSet.getInt(1);
    }
}
