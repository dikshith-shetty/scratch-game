package com.scratchgame.strategy;

import com.scratchgame.config.dto.Symbol;

public interface RewardStrategy {
    double evaluateReward(Symbol symbol, double baseReward);
}
