package com.scratchgame.core;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import com.scratchgame.config.Symbol;
import com.scratchgame.config.WinCombinations;
import com.scratchgame.core.winrule.WinRuleEvaluator;

public class RewardCalculatorTest {

    private final Symbol symbolA = new Symbol("A", 2.0, "standard", null, 0);
    private final Symbol bonusMultiply = new Symbol("BONUS_MUL", 2, "bonus", "multiply_reward", 0);
    private final Symbol bonusExtra = new Symbol("BONUS_EXTRA", 0.0, "bonus", "extra_bonus", 10);
    private final Symbol bonusMiss = new Symbol("BONUS_MISS", 0.0, "bonus", "miss", 0);

    @Test
    void testCalculateReward_multiplyBonus() {
        Map<Symbol, List<WinCombinations>> appliedWins = Map.of(
                symbolA, List.of(new WinCombinations("same_symbol1", 5.0, "same_symbols", 3, "group1", null))
        );
        WinRuleEvaluator evaluator = Mockito.mock(WinRuleEvaluator.class);
        when(evaluator.getEncounteredBonusSymbol()).thenReturn(bonusMultiply);

        RewardCalculator calculator = new RewardCalculator();
        double reward = calculator.calculateReward(appliedWins, 1.0, evaluator);
        assertEquals(20.0, reward, 0.001); // (2.0 * 5.0) * 2.0 * 1.0
    }

    @Test
    void testCalculateReward_extraBonus() {
        Map<Symbol, List<WinCombinations>> appliedWins = Map.of(
                symbolA, List.of(new WinCombinations("linear_symbol1",2.5, "linear_symbols", null, "group2", null))
        );
        WinRuleEvaluator evaluator = Mockito.mock(WinRuleEvaluator.class);
        when(evaluator.getEncounteredBonusSymbol()).thenReturn(bonusExtra);

        RewardCalculator calculator = new RewardCalculator();
        double reward = calculator.calculateReward(appliedWins, 1.0, evaluator);
        assertEquals(15.0, reward, 0.001); // (2.0 * 2.5) + 10.0
    }

    @Test
    void testCalculateReward_missBonus() {
        Map<Symbol, List<WinCombinations>> appliedWins = Map.of(
                symbolA, List.of(new WinCombinations("same_symbol1", 2.5, "same_symbols", 2, "group3", null))
        );
        WinRuleEvaluator evaluator = Mockito.mock(WinRuleEvaluator.class);
        when(evaluator.getEncounteredBonusSymbol()).thenReturn(bonusMiss);

        RewardCalculator calculator = new RewardCalculator();
        double reward = calculator.calculateReward(appliedWins, 1.0, evaluator);
        assertEquals(5, reward, 0.001);
    }

    @Test
    void testCalculateReward_noStandardWinsWithBonus() {
        Map<Symbol, List<WinCombinations>> appliedWins = Map.of();
        WinRuleEvaluator evaluator = Mockito.mock(WinRuleEvaluator.class);
        when(evaluator.getEncounteredBonusSymbol()).thenReturn(bonusMultiply);

        RewardCalculator calculator = new RewardCalculator();
        double reward = calculator.calculateReward(appliedWins, 1.0, evaluator);
        assertEquals(0.0, reward, 0.001);
    }

    @Test
    void testCalculateReward_standardWinsNoBonus() {
        Map<Symbol, List<WinCombinations>> appliedWins = Map.of(
                symbolA, List.of(new WinCombinations("linear_symbol1", 10.0, "linear_symbols", null, "group4", null))
        );
        WinRuleEvaluator evaluator = Mockito.mock(WinRuleEvaluator.class);
        when(evaluator.getEncounteredBonusSymbol()).thenReturn(null);

        RewardCalculator calculator = new RewardCalculator();
        double reward = calculator.calculateReward(appliedWins, 1.0, evaluator);
        assertEquals(20.0, reward, 0.001); // (2.0 * 10.0) * 1.0
    }
}