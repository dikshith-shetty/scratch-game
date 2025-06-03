package com.scratchgame.strategy;

import com.scratchgame.config.dto.Symbol;

public class MissBonusStrategy implements RewardStrategy {
    @Override
    public double evaluateReward(Symbol symbol, double baseReward) {
        return baseReward;
    }
}
