package com.coding.task.database;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

    List<Purchase> findByDateGreaterThan(LocalDate date);

    List<Purchase> findByDateGreaterThanAndCustomerId(LocalDate date, Long customerId);
}
