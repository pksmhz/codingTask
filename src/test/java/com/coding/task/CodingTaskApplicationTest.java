package com.coding.task;

import com.coding.task.api.RewardPointsController;
import com.coding.task.logic.ResultPoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodingTaskApplicationTest {

    public static final long CUSTOMER_1_ID = 1L;
    public static final long CUSTOMER_2_ID = 2L;
    public static final long CUSTOMER_NON_EXISTING_ID = 30L;
    @Autowired
    private RewardPointsController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller, notNullValue());
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getPointsForGivenCustomerTest1() {
        ResultPoints result = this.restTemplate.getForObject("http://localhost:" + port + "/getRewardPoints/" + CUSTOMER_1_ID,
                ResultPoints.class);

        assertEquals(230, result.getTotal());
        assertEquals(70, result.getRewardPointsByMonth().get(7));
        assertEquals(70, result.getRewardPointsByMonth().get(8));
        assertEquals(90, result.getRewardPointsByMonth().get(9));
    }

    @Test
    void getPointsForGivenCustomerTest2() {
        ResultPoints result = this.restTemplate.getForObject("http://localhost:" + port + "/getRewardPoints/" + CUSTOMER_2_ID,
                ResultPoints.class);

        assertEquals(120, result.getTotal());
        assertEquals(10, result.getRewardPointsByMonth().get(7));
        assertEquals(110, result.getRewardPointsByMonth().get(8));
        assertEquals(0, result.getRewardPointsByMonth().get(9));
    }


    @Test
    void getPointsForGivenCustomerNonExistingCustomerId() {

        ResponseEntity<ResultPoints> response =
                restTemplate.exchange("http://localhost:" + port + "/getRewardPoints/" + CUSTOMER_NON_EXISTING_ID,
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
