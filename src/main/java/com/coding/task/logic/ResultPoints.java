package com.coding.task.logic;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class ResultPoints {
    private Long total;
    private Map<Integer, Long> rewardPointsByMonth;

    public ResultPoints(Map<Integer, Long> rewardPointsByMonth) {
        this.rewardPointsByMonth = rewardPointsByMonth;
        total = rewardPointsByMonth
                .values()
                .stream()
                .reduce(0L, Long::sum);
    }
}
