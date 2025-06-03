package com.scratchgame.config;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonusSymbolsProbability {
    private Map<String, Integer> symbols;
}