package tablemodel;

import util.DateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RevenueRecognitionsFabric {

    public static List<RevenueRecognitions> createRevenueRecognitions(Contract contract, Product product) {
        switch (product.getType()) {
            case "W":
                return Collections.singletonList(
                        new RevenueRecognitions(contract.getId(), contract.getRevenue(), contract.getDateSigned())
                );
            case "S": {
                final BigDecimal revenue = contract.getRevenue().divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP);
                return Arrays.asList(
                        new RevenueRecognitions(contract.getId(), revenue, contract.getDateSigned()),
                        new RevenueRecognitions(contract.getId(), revenue, DateUtil.addDays(contract.getDateSigned(), 60)),
                        new RevenueRecognitions(contract.getId(), revenue, DateUtil.addDays(contract.getDateSigned(), 90))
                );
            }
            case "D": {
                final BigDecimal revenue = contract.getRevenue().divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP);
                return Arrays.asList(
                        new RevenueRecognitions(contract.getId(), revenue, contract.getDateSigned()),
                        new RevenueRecognitions(contract.getId(), revenue, DateUtil.addDays(contract.getDateSigned(), 30)),
                        new RevenueRecognitions(contract.getId(), revenue, DateUtil.addDays(contract.getDateSigned(), 60))
                );
            }
            default:
                throw new IllegalArgumentException("unknown product type");
        }
    }
}
