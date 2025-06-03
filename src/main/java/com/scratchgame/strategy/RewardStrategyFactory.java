package com.scratchgame.strategy;

import com.scratchgame.config.dto.*;

public class RewardStrategyFactory {
    private static final RewardStrategy STANDARD = new StandardRewardStrategy();
    private static final RewardStrategy MULTIPLY = new MultiplyRewardStrategy();
    private static final RewardStrategy EXTRA = new ExtraBonusStrategy();
    private static final RewardStrategy MISS = new MissBonusStrategy();



    public static RewardStrategy getStrategy(Symbol symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol cannot be null");
        }
        if (symbol instanceof StandardSymbol) return STANDARD;
        if (symbol instanceof MultiplyRewardBonus) return MULTIPLY;
        if (symbol instanceof ExtraBonus) return EXTRA;
        if (symbol instanceof MissBonus) return MISS;

        throw new IllegalArgumentException("Unknown symbol type: " + symbol.getClass());
    }
}
