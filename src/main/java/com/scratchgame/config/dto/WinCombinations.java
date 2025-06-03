package com.scratchgame.config.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinCombinations {
    private String name;
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    private String when;
    private Integer count;
    private String group;
    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;
}