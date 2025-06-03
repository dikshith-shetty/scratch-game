package com.scratchgame.config.validation;

import java.util.List;

import com.scratchgame.config.dto.Config;

public class ConfigValidationService {

    private final List<ConfigValidator> validators;

    public ConfigValidationService(List<ConfigValidator> validators) {
        this.validators = validators;
    }

    public void validate(Config config) {
        for (ConfigValidator validator : validators) {
            validator.validate(config);
        }
    }
}