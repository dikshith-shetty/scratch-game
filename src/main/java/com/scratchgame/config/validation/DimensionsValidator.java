package com.scratchgame.config.validation;

import com.scratchgame.config.dto.Config;

public class DimensionsValidator implements ConfigValidator {
    @Override
    public void validate(Config config) {
        // The smallest matrix should have 3 cells. We have to place minimum 1 standard symbol and minimum 1 bonus symbol.
        // So assumed that at least we need 3 sells to play the game.

        if(config.getColumns() * config.getRows() < 3) {
            throw new IllegalArgumentException("Columns and rows must be positive integers.");
        }

    }
}