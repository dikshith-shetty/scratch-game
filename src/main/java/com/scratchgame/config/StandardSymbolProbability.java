package com.scratchgame.config;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StandardSymbolProbability {

    private int column;
    private int row;
    private Map<String, Integer> symbols;
}