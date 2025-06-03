package com.scratchgame.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Probabilities {

    @JsonProperty("standard_symbols")
    private List<StandardSymbolProbability> standardSymbols;
    @JsonProperty("bonus_symbols")
    private BonusSymbolsProbability bonusSymbols;
}