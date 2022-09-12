package com.coding.task.logic;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

//@AllArgsConstructor
@NoArgsConstructor
public class ResultPoints {
    @Getter
    private Long total;
    @Getter
    private Map<Integer, Long> rewardPointsByMonth;

    public ResultPoints(Map<Integer, Long> rewardPointsByMonth) {
        this.rewardPointsByMonth = rewardPointsByMonth;
        total = rewardPointsByMonth
                .values()
                .stream()
                .reduce(0L, Long::sum);
    }
}
