package com.coding.task.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    CustomerName findNameById(Long id);
}