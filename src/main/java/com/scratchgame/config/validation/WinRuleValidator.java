package com.scratchgame.config.validation;

import com.scratchgame.config.dto.Config;

public class WinRuleValidator implements ConfigValidator {
    @Override
    public void validate(Config config) {
        if(config.getWinCombinations() == null || config.getWinCombinations().isEmpty()) {
            throw new IllegalArgumentException("WinCombinations cannot be empty.");
        }
    }
}
