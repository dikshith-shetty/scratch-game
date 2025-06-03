package com.scratchgame.config.validation;

import com.scratchgame.config.dto.Config;

public interface ConfigValidator  {
    void validate(Config config);
}