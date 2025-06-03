package com.scratchgame.config.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scratchgame.enums.SymbolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardSymbol extends Symbol {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;

    public StandardSymbol(String symbolId, double rewardMultiplier) {
        super(symbolId, SymbolType.STANDARD);
        this.rewardMultiplier = rewardMultiplier;
    }
}