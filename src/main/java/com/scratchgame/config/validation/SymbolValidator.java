package com.scratchgame.config.validation;

import java.util.Map;

import com.scratchgame.config.Config;
import com.scratchgame.config.Symbol;

public class SymbolValidator implements ConfigValidator {

    @Override
    public void validate(Config config) {
        Map<String, Symbol> symbols = config.getSymbols();
        if (symbols == null || symbols.isEmpty()) {
            throw new IllegalArgumentException("Symbols cannot be empty.");
        }
    }
}