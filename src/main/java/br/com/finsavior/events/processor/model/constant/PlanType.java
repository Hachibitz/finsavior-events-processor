package br.com.finsavior.events.processor.model.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PlanType {
    FREE("1L"),
    PLUS("P-2BA47576SC596340BMZMJJRQ"),
    PREMIUM("3L");

    private final String planTypeId;

    PlanType(String planTypeId) {
        this.planTypeId = planTypeId;
    }

    public static PlanType fromValue(String value) {
        return Arrays.stream(PlanType.values())
                .filter(planType -> planType.planTypeId.equals(value))
                .findFirst()
                .orElse(null);
    }
}
