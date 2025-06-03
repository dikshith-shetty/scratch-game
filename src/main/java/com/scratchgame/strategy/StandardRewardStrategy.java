package com.scratchgame.strategy;

import com.scratchgame.config.dto.StandardSymbol;
import com.scratchgame.config.dto.Symbol;

public class StandardRewardStrategy implements RewardStrategy {
    @Override
    public double evaluateReward(Symbol symbol, double baseReward) {
        return baseReward *  ((StandardSymbol)symbol).getRewardMultiplier();
    }
}
