package com.scratchgame.core;

import java.util.List;
import java.util.Map;

import com.scratchgame.config.dto.Config;
import com.scratchgame.config.dto.Symbol;
import com.scratchgame.config.dto.WinCombinations;
import com.scratchgame.core.winrule.WinRuleEvaluator;

public class GameEngine {

    private final Config config;
    final MatrixGenerator matrixGenerator;
    private final WinRuleEvaluator winRuleEvaluator;
    private final RewardCalculator rewardCalculator;

    public GameEngine(Config config, MatrixGenerator matrixGenerator, WinRuleEvaluator winRuleEvaluator, RewardCalculator rewardCalculator) {
        this.config = config;
        this.matrixGenerator = matrixGenerator;
        this.winRuleEvaluator = winRuleEvaluator;
        this.rewardCalculator = rewardCalculator;
    }
    
    public GameResult play(double betAmount) {
        List<List<Symbol>> matrix = matrixGenerator.generateMatrix();
        Map<Symbol, List<WinCombinations>> appliedWinCombinations = winRuleEvaluator.evaluate(matrix, config);
        double reward = rewardCalculator.calculateReward(appliedWinCombinations, betAmount, winRuleEvaluator);
        return new GameResult(matrix, appliedWinCombinations, reward, winRuleEvaluator.getEncounteredBonusSymbol());
    }

}