package com.scratchgame.strategy;

import com.scratchgame.config.dto.MultiplyRewardBonus;
import com.scratchgame.config.dto.Symbol;

public class MultiplyRewardStrategy implements RewardStrategy {
    @Override
    public double evaluateReward(Symbol symbol, double baseReward) {
        return baseReward * ((MultiplyRewardBonus)symbol).getRewardMultiplier();
    }
}
