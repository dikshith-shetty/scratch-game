package com.scratchgame.core;

import java.util.List;
import java.util.Map;

import com.scratchgame.config.BonusImpact;
import com.scratchgame.config.Symbol;
import com.scratchgame.config.WinCombinations;
import com.scratchgame.core.winrule.WinRuleEvaluator;

public class RewardCalculator {

    public double calculateReward(Map<Symbol, List<WinCombinations>> appliedWinCombinations,
                                  double betAmount, WinRuleEvaluator evaluator) {
        double totalReward = 0;
        boolean hasStandardWin = !appliedWinCombinations.isEmpty();
        Symbol bonusSymbol = evaluator.getEncounteredBonusSymbol();

        // Process standard win combinations
        for (Map.Entry<Symbol, List<WinCombinations>> entry : appliedWinCombinations.entrySet()) {
            Symbol symbol = entry.getKey();
            List<WinCombinations> wins = entry.getValue();
            double symbolReward = symbol.getRewardMultiplier();

            for (WinCombinations win : wins) {
                symbolReward *= win.getRewardMultiplier();
            }
            totalReward += symbolReward;
        }
        totalReward *= betAmount;

        // Handle bonus symbol effects
        if (bonusSymbol != null && hasStandardWin && bonusSymbol.getImpact() != null) {
            BonusImpact impact = BonusImpact.fromValue(bonusSymbol.getImpact());
            if (impact != null) {
                switch (impact) {
                    case MULTIPLY_REWARD:
                        totalReward *= bonusSymbol.getRewardMultiplier();
                        break;
                    case EXTRA_BONUS:
                        totalReward += bonusSymbol.getExtra();
                        break;
                    case MISS:
                        break;
                    default:
                        System.out.println("Unknown bonus impact: " + bonusSymbol.getImpact());
                }
            } else {
                System.out.println("Unknown bonus impact in config: " + bonusSymbol.getImpact());
            }
        }

        return totalReward;
    }
}
