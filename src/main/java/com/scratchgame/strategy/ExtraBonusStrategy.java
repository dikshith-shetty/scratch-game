package com.scratchgame.strategy;

import com.scratchgame.config.dto.ExtraBonus;
import com.scratchgame.config.dto.Symbol;

public class ExtraBonusStrategy implements RewardStrategy {
    @Override
    public double evaluateReward(Symbol symbol, double baseReward) {
        return baseReward + ((ExtraBonus)symbol).getExtra();
    }
}
