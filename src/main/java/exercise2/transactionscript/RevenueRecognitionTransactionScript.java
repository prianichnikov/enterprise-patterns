package exercise2.transactionscript;

import exercise2.util.DateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;

public class RevenueRecognitionTransactionScript {

    private static final String INSERT_REVENUE_RECOGNITION = "insert into revenuerecognitions values(?, ?, ?)";
    private static final String SELECT_CONTRACT_BY_ID = "select id,product,revenue,datesigned from contracts where id = ?";
    private static final String SELECT_PRODUCT_BY_ID = "select id,name,type from products where id = ?";

    private final Connection connection;

    public RevenueRecognitionTransactionScript(Connection connection) {
        this.connection = connection;
    }

    public void calculateByContractId(int contractId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONTRACT_BY_ID);
            preparedStatement.setInt(1, contractId);
            final ResultSet contractResultSet = preparedStatement.executeQuery();
            int productId;
            BigDecimal contractRevenue;
            Date contractDate;

            if (contractResultSet.first()) {
                productId = contractResultSet.getInt("product");
                contractRevenue = contractResultSet.getBigDecimal("revenue");
                contractDate = contractResultSet.getDate("datesigned");
            } else {
                System.err.println(String.format("Contract with id: %d not found", contractId));
                return;
            }

            preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1, productId);
            final ResultSet productResultSet = preparedStatement.executeQuery();
            String productType;
            if (productResultSet.first()) {
                productType = productResultSet.getString("type");
            } else {
                System.err.println(String.format("Product with id %d not found", productId));
                return;
            }

            if (productType.equals("D")) {
                final BigDecimal partOfRevenue = contractRevenue.divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP);
                PreparedStatement insertRevenueRecognition = connection.prepareStatement(INSERT_REVENUE_RECOGNITION);

                insertRevenueRecognition.setInt(1, contractId);
                insertRevenueRecognition.setBigDecimal(2, partOfRevenue);
                insertRevenueRecognition.setDate(3, contractDate);
                insertRevenueRecognition.execute();

                insertRevenueRecognition.setInt(1, contractId);
                insertRevenueRecognition.setBigDecimal(2, partOfRevenue);
                insertRevenueRecognition.setDate(3, DateUtil.addDays(contractDate, 30));
                insertRevenueRecognition.execute();

                insertRevenueRecognition.setInt(1, contractId);
                insertRevenueRecognition.setBigDecimal(2, partOfRevenue);
                insertRevenueRecognition.setDate(3, DateUtil.addDays(contractDate, 60));
                insertRevenueRecognition.execute();
            } else if (productType.equals("S")) {
                BigDecimal partOfRevenue = contractRevenue.divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP);
                PreparedStatement insertRevenueRecognition = connection.prepareStatement(INSERT_REVENUE_RECOGNITION);

                insertRevenueRecognition.setInt(1, contractId);
                insertRevenueRecognition.setBigDecimal(2, partOfRevenue);
                insertRevenueRecognition.setDate(3, contractDate);
                insertRevenueRecognition.execute();

                insertRevenueRecognition.setInt(1, contractId);
                insertRevenueRecognition.setBigDecimal(2, partOfRevenue);
                insertRevenueRecognition.setDate(3, DateUtil.addDays(contractDate, 60));
                insertRevenueRecognition.execute();

                insertRevenueRecognition.setInt(1, contractId);
                insertRevenueRecognition.setBigDecimal(2, partOfRevenue);
                insertRevenueRecognition.setDate(3, DateUtil.addDays(contractDate, 90));
                insertRevenueRecognition.execute();
            } else {
                PreparedStatement insertRevenueRecognition = connection.prepareStatement(INSERT_REVENUE_RECOGNITION);
                insertRevenueRecognition.setInt(1, contractId);
                insertRevenueRecognition.setBigDecimal(2, contractRevenue);
                insertRevenueRecognition.setDate(3, contractDate);
                insertRevenueRecognition.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
