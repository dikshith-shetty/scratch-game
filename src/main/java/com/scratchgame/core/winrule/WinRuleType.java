package com.scratchgame.core.winrule;

public enum WinRuleType {
    SAME_SYMBOLS("same_symbols"),
    LINEAR_SYMBOLS("linear_symbols");

    private final String value;

    WinRuleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static WinRuleType fromValue(String value) {
        for (WinRuleType type : WinRuleType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}