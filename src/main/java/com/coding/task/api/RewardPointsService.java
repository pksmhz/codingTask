package com.coding.task.api;

import com.coding.task.database.CustomerName;
import com.coding.task.database.CustomerRepository;
import com.coding.task.database.Purchase;
import com.coding.task.database.PurchaseRepository;
import com.coding.task.logic.ResultPoints;
import com.coding.task.logic.RewardPointsCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardPointsService {

    final PurchaseRepository purchaseRepository;

    final CustomerRepository customerRepository;

    final RewardPointsCalculator rewardPointsCalculator;

    public RewardPointsService(PurchaseRepository purchaseRepository, CustomerRepository customerRepository, RewardPointsCalculator rewardPointsCalculator) {
        this.purchaseRepository = purchaseRepository;
        this.customerRepository = customerRepository;
        this.rewardPointsCalculator = rewardPointsCalculator;
    }

    public Map<String, ResultPoints>  getRewardPoints() {
        List<Purchase> purchases = purchaseRepository.findByDateGreaterThan(LocalDate.now().minusMonths(3));
        Map<Long, ResultPoints> rewardPoints = rewardPointsCalculator.calculateRewardPointsForAllCustomers(purchases);

        return rewardPoints.entrySet().stream()
                .collect(Collectors.toMap(x -> customerRepository.findNameById(x.getKey()).getName(), Map.Entry::getValue));
    }

    public ResultPoints getRewardPoints(long customerId) throws CustomerIdNotFoundException {
        // check if given customer exists
        CustomerName nameById = customerRepository.findNameById(customerId);
        if (nameById == null) {
            throw new CustomerIdNotFoundException();
        }
        List<Purchase> purchases = purchaseRepository.findByDateGreaterThanAndCustomerId(LocalDate.now().minusMonths(20), customerId);

        return rewardPointsCalculator.calculateRewardsPointsForSingleCustomer(purchases);
    }

}
