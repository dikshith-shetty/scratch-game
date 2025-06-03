package com.scratchgame.core;

import java.util.List;
import java.util.Map;

import com.scratchgame.config.dto.BonusSymbol;
import com.scratchgame.config.dto.Symbol;
import com.scratchgame.config.dto.WinCombinations;
import com.scratchgame.core.winrule.WinRuleEvaluator;
import com.scratchgame.strategy.RewardStrategyFactory;

public class RewardCalculator {

    public double calculateReward(Map<Symbol, List<WinCombinations>> appliedWinCombinations,
                                  double betAmount, WinRuleEvaluator evaluator) {

        boolean hasStandardWin = !appliedWinCombinations.isEmpty();
        if(!hasStandardWin) {
            return 0;
        }
        double totalReward = 0;
        BonusSymbol bonusSymbol = (BonusSymbol) evaluator.getEncounteredBonusSymbol();

        // Process standard win combinations
        for (Map.Entry<Symbol, List<WinCombinations>> entry : appliedWinCombinations.entrySet()) {
            Symbol symbol = entry.getKey();
            List<WinCombinations> wins = entry.getValue();
            double symbolReward = RewardStrategyFactory.getStrategy(symbol).evaluateReward(symbol,betAmount);

            for (WinCombinations win : wins) {
                symbolReward *= win.getRewardMultiplier();
            }
            totalReward += symbolReward;
        }

        // Handle bonus symbol effects
        totalReward = RewardStrategyFactory.getStrategy(bonusSymbol).evaluateReward(bonusSymbol, totalReward);

        return totalReward;
    }
}
