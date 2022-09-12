package com.coding.task.logic;

import com.coding.task.database.Purchase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
public class RewardPointsCalculator {
    public static final int BONUS_POINTS_OVER_100 = 2;
    public static final int BONUS_POINTS_OVER_50 = 1;

    public Map<Long, ResultPoints> calculateRewardPointsForAllCustomers(List<Purchase> purchases) {
        Map<Long, List<Purchase>> purchasesByCustomer = purchases.stream().collect(groupingBy(Purchase::getCustomerId));

        return purchasesByCustomer.entrySet()
                .stream().collect(Collectors.toMap(Map.Entry::getKey, x -> calculateRewardsPointsForSingleCustomer(x.getValue())));


    }

    public ResultPoints calculateRewardsPointsForSingleCustomer(List<Purchase> purchases) {
        return new ResultPoints(purchases.stream().collect(groupingBy(Purchase::getMonth))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().stream()
                        .map(purchase -> calculateRewardsPointsForOnePurchase(purchase.getAmount()))
                        .reduce(0L, Long::sum))));
    }

    private long calculateRewardsPointsForOnePurchase(BigDecimal input) {
        long value = input.longValue();
        long result = 0;

        if (value >= 100) {
            result += (value - 100) * BONUS_POINTS_OVER_100;
            result += 50 * BONUS_POINTS_OVER_50; // points for
            return result;
        }

        if (value >= 50) {
            result += (value - 50) * BONUS_POINTS_OVER_50;
            return result;
        }
        return result;
    }
}
