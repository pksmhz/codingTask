package com.coding.task.api;

import com.coding.task.logic.ResultPoints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@RestController
public class RewardPointsController {

    @Autowired
    RewardPointsService service;

    @GetMapping(value = "/getRewardPoints")
    public Map<String, ResultPoints> getRewardPoints() {
        return service.getRewardPoints();
    }

    @GetMapping(value = "/getRewardPoints/{customerId}")
    public ResultPoints getRewardPoints(@PathVariable long customerId) {
        try {
            return service.getRewardPoints(customerId);
        } catch (CustomerIdNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer with given ID not found", e);
        }
    }
}
