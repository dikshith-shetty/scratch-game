package com.scratchgame.config.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scratchgame.enums.BonusImpactType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiplyRewardBonus extends BonusSymbol {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;

    public MultiplyRewardBonus(String symbolId, double rewardMultiplier) {
        super(symbolId, BonusImpactType.MULTIPLY_REWARD);
        this.rewardMultiplier = rewardMultiplier;
    }
}