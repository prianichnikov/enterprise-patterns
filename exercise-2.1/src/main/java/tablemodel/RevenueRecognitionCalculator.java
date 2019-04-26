package tablemodel;

import java.sql.*;
import java.util.List;

public class RevenueRecognitionCalculator {

    private final Connection connection;

    public RevenueRecognitionCalculator(Connection connection) {
        this.connection = connection;
    }

    public void calculateByContractId(int contractId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select id,product,revenue,datesigned from contracts where id = ?");
            ps.setInt(1, contractId);
            ResultSet resultSet = ps.executeQuery();
            Contract contract = Contract.getContractFromResultSet(resultSet);

            ps = connection.prepareStatement("select id,name,type from products where id = ?");
            ps.setInt(1, contract.getProduct());
            resultSet = ps.executeQuery();
            Product product = Product.getProductFromResultSet(resultSet);

            List<RevenueRecognitions> revenueRecognitionsList =
                    RevenueRecognitionsFabric.createRevenueRecognitions(contract, product);

            ps = connection.prepareStatement("insert into revenuerecognitions values (?, ?, ?)");
            for (RevenueRecognitions revenueRecognitions : revenueRecognitionsList) {
                ps.setInt(1, revenueRecognitions.getContract());
                ps.setBigDecimal(2, revenueRecognitions.getAmount());
                ps.setDate(3, revenueRecognitions.getRecognizedOn());
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
