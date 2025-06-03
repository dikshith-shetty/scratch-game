package com.scratchgame.config.validation;

import java.util.Map;

import com.scratchgame.config.dto.Config;
import com.scratchgame.config.dto.Symbol;
import com.scratchgame.enums.SymbolType;

public class SymbolValidator implements ConfigValidator {

    @Override
    public void validate(Config config) {
        Map<String, Symbol> symbols = config.getSymbols();
        if (symbols == null || symbols.isEmpty()) {
            throw new IllegalArgumentException("Symbols cannot be empty.");
        }

        boolean hasStandard = symbols.values().stream()
                .anyMatch(s -> s.getType() == SymbolType.STANDARD);

        if (!hasStandard) {
            throw new IllegalArgumentException("Config must contain at least one STANDARD symbol");
        }

        boolean hasBonus = symbols.values().stream()
                .anyMatch(s -> s.getType() == SymbolType.BONUS);

        if (!hasBonus) {
            throw new IllegalArgumentException("Config must contain at least one BONUS symbol");
        }
    }
}