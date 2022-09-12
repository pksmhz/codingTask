package com.coding.task.logic;

import com.coding.task.database.Customer;
import com.coding.task.database.Purchase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RewardsPointsTest {

    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final long CUSTOMER1_ID = 1L;
    public static final long CUSTOMER2_ID = 2L;
    @Autowired
    RewardPointsCalculator rewardsPoints;

    static Customer testCustomer1;
    static Customer testCustomer2;
    static LocalDate dateJuly;
    static LocalDate dateAugust;
    static LocalDate dateSeptember;
    static LocalDate dateJanuary;

    @BeforeAll
    public static void setup() {
        testCustomer1 = new Customer(1L, "customer1");
        testCustomer2 = new Customer(2L, "customer2");
        dateJuly = LocalDate.of(2022, 7, 11);
        dateAugust = LocalDate.of(2022, 8, 11);
        dateSeptember = LocalDate.of(2022, 9, 11);
        dateJanuary = LocalDate.of(2022, 9, 11);
    }

    @Test
    void calculateRewardsPoints_value_greater_than_100() {
        List<Purchase> purchases = List.of(new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("120.0")));

        assertEquals(90, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_120_with_value_after_decimal_point() {
        List<Purchase> purchases = List.of(new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("120.8")));

        assertEquals(90, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_value_50() {
        List<Purchase> purchases = List.of(new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("50")));

        assertEquals(0, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_value_51() {
        List<Purchase> purchases = List.of(new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("51")));

        assertEquals(1, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_value_greater_than_50() {
        List<Purchase> purchases = List.of(new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("60")));

        assertEquals(10, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_value_less_than_50() {
        List<Purchase> purchases = List.of(new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("40")));

        assertEquals(0, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_value_101() {
        List<Purchase> purchases = List.of(new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("101")));

        assertEquals(52, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_value_500() {
        List<Purchase> purchases = List.of(new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("500")));

        assertEquals(850, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_two_transactions_over_120() {
        List<Purchase> purchases = Arrays.asList(
                new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("120.0")),
                new Purchase(2L, testCustomer1, 1L, dateSeptember, new BigDecimal("120.0")));

        assertEquals(180, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_six_transactions() {
        List<Purchase> purchases = Arrays.asList(
                new Purchase(1L, testCustomer1, 1L, dateSeptember, new BigDecimal("120.0")),
                new Purchase(2L, testCustomer1, 1L, dateSeptember, new BigDecimal("60")),
                new Purchase(3L, testCustomer1, 1L, dateSeptember, new BigDecimal("60")),
                new Purchase(4L, testCustomer1, 1L, dateSeptember, new BigDecimal("30")),
                new Purchase(5L, testCustomer1, 1L, dateSeptember, new BigDecimal("10")),
                new Purchase(6L, testCustomer1, 1L, dateSeptember, new BigDecimal("500"))
        );

        assertEquals(110 + 850, rewardsPoints.calculateRewardsPointsForSingleCustomer(purchases).getRewardPointsByMonth().get(SEPTEMBER));
    }

    @Test
    void calculateRewardsPoints_six_transactions_different_users() {
        List<Purchase> purchases = Arrays.asList(
                new Purchase(1L, testCustomer1, 1L, dateJuly, new BigDecimal("120.0")),
                new Purchase(2L, testCustomer1, 1L, dateAugust, new BigDecimal("60")),
                new Purchase(3L, testCustomer1, 1L, dateSeptember, new BigDecimal("60")),
                new Purchase(4L, testCustomer2, 2L, dateJuly, new BigDecimal("30")),
                new Purchase(5L, testCustomer2, 2L, dateAugust, new BigDecimal("10")),
                new Purchase(6L, testCustomer2, 2L, dateSeptember, new BigDecimal("500"))
        );

        Map<Long, ResultPoints> result = rewardsPoints.calculateRewardPointsForAllCustomers(purchases);

        assertEquals(90L, result.get(CUSTOMER1_ID).getRewardPointsByMonth().get(JULY));
        assertEquals(10L, result.get(CUSTOMER1_ID).getRewardPointsByMonth().get(AUGUST));
        assertEquals(10L, result.get(CUSTOMER1_ID).getRewardPointsByMonth().get(SEPTEMBER));
        assertEquals(110, result.get(CUSTOMER1_ID).getTotal());
        assertEquals(0L, result.get(CUSTOMER2_ID).getRewardPointsByMonth().get(JULY));
        assertEquals(0L, result.get(CUSTOMER2_ID).getRewardPointsByMonth().get(AUGUST));
        assertEquals(850L, result.get(CUSTOMER2_ID).getRewardPointsByMonth().get(SEPTEMBER));
        assertEquals(850, result.get(CUSTOMER2_ID).getTotal());
    }

}