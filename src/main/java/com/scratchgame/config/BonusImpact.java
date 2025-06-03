package com.scratchgame.config;

public enum BonusImpact {
    MULTIPLY_REWARD("multiply_reward"),
    EXTRA_BONUS("extra_bonus"),
    MISS("miss");

    private final String value;

    BonusImpact(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BonusImpact fromValue(String value) {
        for (BonusImpact impact : BonusImpact.values()) {
            if (impact.value.equals(value)) {
                return impact;
            }
        }
        return null;
    }
}