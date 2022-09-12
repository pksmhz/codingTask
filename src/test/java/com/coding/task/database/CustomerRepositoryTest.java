package com.coding.task.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"})
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void findCustomerNameByIdTest() {
        CustomerName customerName1 = customerRepository.findNameById(1L);
        assertEquals("TEST_CUSTOMER_1", customerName1.getName());

        CustomerName customerName2 = customerRepository.findNameById(2L);
        assertEquals("TEST_CUSTOMER_2", customerName2.getName());
    }
}