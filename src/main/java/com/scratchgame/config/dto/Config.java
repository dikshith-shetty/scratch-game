package com.scratchgame.config.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scratchgame.util.SymbolMapDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config {

    private int columns;
    private int rows;
    @JsonDeserialize(using = SymbolMapDeserializer.class)
    private Map<String, Symbol> symbols;
    private Probabilities probabilities;
    @JsonProperty("win_combinations")
    private Map<String, WinCombinations> winCombinations;
}