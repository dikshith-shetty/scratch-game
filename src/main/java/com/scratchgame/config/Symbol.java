package com.scratchgame.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Symbol {
    private String symbolId; // Using symbolId
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    private String type;
    private String impact;
    private int extra;
}