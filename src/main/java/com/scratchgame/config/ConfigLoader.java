package com.scratchgame.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchgame.config.dto.Config;
import com.scratchgame.config.dto.Symbol;
import com.scratchgame.config.dto.WinCombinations;
import com.scratchgame.config.validation.ConfigValidationService;

public class ConfigLoader {

    private final ConfigValidationService validationService;

    public ConfigLoader(ConfigValidationService validationService) {
        this.validationService = validationService;
    }

    public Config load(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(new File(filePath), Config.class);
        if(validationService != null) {
            validationService.validate(config);
        }
        // Set symbolId for each Symbol
        if (config.getSymbols() != null) {
            for (Map.Entry<String, Symbol> entry : config.getSymbols().entrySet()) {
                entry.getValue().setSymbolId(entry.getKey());
            }
        }

        if(config.getWinCombinations() != null) {
            for (Map.Entry<String, WinCombinations> entry : config.getWinCombinations().entrySet()) {
                entry.getValue().setName(entry.getKey());
            }
        }
        return config;
    }
}