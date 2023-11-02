package com.coding.task.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"})
class PurchaseRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource, notNullValue());
        assertThat(jdbcTemplate, notNullValue());
        assertThat(entityManager, notNullValue());
        assertThat(purchaseRepository, notNullValue());
    }

    @Test
    void findByDateGreaterThanTest() {
        List<Purchase> result = purchaseRepository.findByDateGreaterThan(LocalDate.of(2022, 5, 11));

        assertThat(result, notNullValue());
        assertThat(result, hasSize(6));
        assertThat(result, containsInAnyOrder(
                hasProperty("date", is(LocalDate.of(2022, 9, 1))),
                hasProperty("date", is(LocalDate.of(2022, 9, 2))),
                hasProperty("date", is(LocalDate.of(2022, 8, 1))),
                hasProperty("date", is(LocalDate.of(2022, 8, 2))),
                hasProperty("date", is(LocalDate.of(2022, 7, 1))),
                hasProperty("date", is(LocalDate.of(2022, 7, 2)))));

    }

    @Test
    void findByDateGreaterThanAndCustomerIdTest() {
        List<Purchase> result = purchaseRepository.findByDateGreaterThanAndCustomerId(LocalDate.of(2022, 5, 11), 1L);

        assertThat(result, notNullValue());
        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(
                hasProperty("date", is(LocalDate.of(2022, 9, 1))),
                hasProperty("date", is(LocalDate.of(2022, 8, 1))),
                hasProperty("date", is(LocalDate.of(2022, 7, 1)))));

    }

}
