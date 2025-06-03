package com.scratchgame.enums;

import lombok.Getter;

public enum SymbolType {
    STANDARD("standard"),
    BONUS("bonus");

    @Getter
    private final String value;

    SymbolType(String value) {
        this.value = value;
    }

    public static SymbolType fromString(String type) {
        for (SymbolType t : values()) {
            if (t.value.equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown symbol type: " + type);
    }
}
