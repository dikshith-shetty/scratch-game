package com.scratchgame.enums;

import lombok.Getter;


public enum BonusImpactType {
    MULTIPLY_REWARD("multiply_reward"),
    EXTRA_BONUS("extra_bonus"),
    MISS("miss");

    @Getter
    private final String value;

    BonusImpactType(String value) {
        this.value = value;
    }

    public static BonusImpactType fromString(String type) {
        for (BonusImpactType t : values()) {
            if (t.value.equals(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown symbol type: " + type);
    }
}