package com.scratchgame.config.validation;

import com.scratchgame.config.Config;

public class DimensionsValidator implements ConfigValidator {

    @Override
    public void validate(Config config) {
        if (config.getColumns() <= 0 || config.getRows() <= 0) {
            throw new IllegalArgumentException("Columns and rows must be positive integers.");
        }
        if(config.getColumns() < 2) {
            throw new IllegalArgumentException("Columns must be at least 2.");
        }
    }
}