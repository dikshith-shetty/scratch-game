package com.scratchgame.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.scratchgame.config.dto.Config;
import com.scratchgame.config.ConfigLoader;
import com.scratchgame.config.dto.Symbol;
import com.scratchgame.core.winrule.WinRuleEvaluator;

public class GameEngineTest {

    Config config = new ConfigLoader(null).load("src/test/resources/config-3x3.json");

    public GameEngineTest() throws IOException {
    }

    /*
     * "bet_amount": 100
     * "matrix": [
     *     ["A", "A", "B"],
     *     ["A", "+1000", "B"],
     *     ["A", "A", "B"]
     *  ],
     *
     * | reward name             | reward details                    |
     * |-------------------------|-----------------------------------|
     * | symbol_A                | bet_amount x5                     |
     * | symbol_B                | bet_amount x3                     |
     * | same_symbol_5_times     | (reward for a specific symbol) x2 |
     * | same_symbol_3_times     | (reward for a specific symbol) x1 |
     * | same_symbols_vertically | (reward for a specific symbol) x2 |
     * | +1000                   | add 1000 extra to final reward    |
     *
     * Calculations:
     *       total = ((bet_amount x reward(symbol_A) x reward(same_symbol_5_times) x reward(same_symbols_vertically))
     *               + (bet_amount x reward(symbol_B) x reward(same_symbol_3_times) x reward(same_symbols_vertically)))
     *               + reward(+1000)
     *             = ((100 x5 x2 x2) + (100 x3 x1 x2)) + 1000
     *             = (2000 + 600) + 1000 = 3600
     */

    @Test
    void testPlayWithSpecificCase1() throws Exception {


        // Mock the MatrixGenerator
        MatrixGenerator matrixGenerator = Mockito.mock(MatrixGenerator.class);
        List<List<Symbol>> mockMatrix = List.of(
                List.of(config.getSymbols().get("A"), config.getSymbols().get("A"), config.getSymbols().get("B")),
                List.of(config.getSymbols().get("A"), config.getSymbols().get("+1000"), config.getSymbols().get("B")),
                List.of(config.getSymbols().get("A"), config.getSymbols().get("A"), config.getSymbols().get("B"))
        );
        when(matrixGenerator.generateMatrix()).thenReturn(mockMatrix);

        WinRuleEvaluator winRuleEvaluator = new WinRuleEvaluator();
        RewardCalculator rewardCalculator = new RewardCalculator();
        // Create the GameEngine with mocked dependencies
        GameEngine gameEngine = new GameEngine(config, matrixGenerator, winRuleEvaluator, rewardCalculator);

        // Execute the game
        GameResult result = gameEngine.play(100.0);


        // Assertions
        List<List<String>> expectedMatrix = List.of(
                List.of("A", "A", "B"),
                List.of("A", "+1000", "B"),
                List.of("A", "A", "B")
        );
        assertEquals(expectedMatrix, result.getMatrix());
        assertEquals(3600.0, result.getReward(), 0.001);

        Map<String, List<String>> expectedAppliedWinCombinations = Map.of(
                "A", List.of("same_symbol_5_times", "same_symbols_vertically"),
                "B", List.of("same_symbol_3_times", "same_symbols_vertically")
        );
        for (String key : expectedAppliedWinCombinations.keySet()) {
            List<String> expectedList = expectedAppliedWinCombinations.get(key);
            List<String> actualList = result.getAppliedWinningCombinations().get(key);

            assertTrue(actualList.containsAll(expectedList) && expectedList.containsAll(actualList),
                    "Lists for key '" + key + "' do not have the same elements");
            assertEquals(expectedList.size(), actualList.size(), "Lists for key '" + key + "' have different sizes");
        }

        assertEquals("+1000", result.getAppliedBonusSymbol());
    }

    /*
     * "bet_amount": 100
     * "matrix" : [
     *     ["A", "B", "C"],
     *     ["E", "B", "5x"],
     *     ["F", "D", "C"]
     * ],
     *
     * "reward": 0 |No win combinations
     *
     */
    @Test
    void testPlayWithSpecificCase2() throws Exception {


        // Mock the MatrixGenerator
        MatrixGenerator matrixGenerator = Mockito.mock(MatrixGenerator.class);
        List<List<Symbol>> mockMatrix = List.of(
                List.of(config.getSymbols().get("A"), config.getSymbols().get("B"), config.getSymbols().get("C")),
                List.of(config.getSymbols().get("E"), config.getSymbols().get("B"), config.getSymbols().get("5x")),
                List.of(config.getSymbols().get("F"), config.getSymbols().get("D"), config.getSymbols().get("C"))
        );
        when(matrixGenerator.generateMatrix()).thenReturn(mockMatrix);

        WinRuleEvaluator winRuleEvaluator = new WinRuleEvaluator();
        RewardCalculator rewardCalculator = new RewardCalculator();
        // Create the GameEngine with mocked dependencies
        GameEngine gameEngine = new GameEngine(config, matrixGenerator, winRuleEvaluator, rewardCalculator);

        // Execute the game
        GameResult result = gameEngine.play(100.0);


        // Assertions
        List<List<String>> expectedMatrix = List.of(
                List.of("A", "B", "C"),
                List.of("E", "B", "5x"),
                List.of("F", "D", "C")
        );
        assertEquals(expectedMatrix, result.getMatrix());
        assertEquals(0.0, result.getReward(), 0.001);
    }

    /*
     * "bet_amount": 100
     * "matrix": [
     *     ["A", "B", "C"],
     *     ["E", "B", "10x"],
     *     ["F", "D", "B"]
     * ],
     *
     * "reward": 3000,
     * "applied_winning_combinations": {
     *      "B": ["same_symbol_3_times"]
     * },
     * "applied_bonus_symbol": "10x"
     *
     */
    @Test
    void testPlayWithSpecificCase3() throws Exception {


        // Mock the MatrixGenerator
        MatrixGenerator matrixGenerator = Mockito.mock(MatrixGenerator.class);
        List<List<Symbol>> mockMatrix = List.of(
                List.of(config.getSymbols().get("A"), config.getSymbols().get("B"), config.getSymbols().get("C")),
                List.of(config.getSymbols().get("E"), config.getSymbols().get("B"), config.getSymbols().get("10x")),
                List.of(config.getSymbols().get("F"), config.getSymbols().get("D"), config.getSymbols().get("B"))
        );
        when(matrixGenerator.generateMatrix()).thenReturn(mockMatrix);

        WinRuleEvaluator winRuleEvaluator = new WinRuleEvaluator();
        RewardCalculator rewardCalculator = new RewardCalculator();
        // Create the GameEngine with mocked dependencies
        GameEngine gameEngine = new GameEngine(config, matrixGenerator, winRuleEvaluator, rewardCalculator);

        // Execute the game
        GameResult result = gameEngine.play(100.0);


        // Assertions
        List<List<String>> expectedMatrix = List.of(
                List.of("A", "B", "C"),
                List.of("E", "B", "10x"),
                List.of("F", "D", "B")
        );
        assertEquals(expectedMatrix, result.getMatrix());
        assertEquals(3000.0, result.getReward(), 0.001);

        Map<String, List<String>> expectedAppliedWinCombinations = Map.of(
                "B", List.of("same_symbol_3_times")
        );
        for (String key : expectedAppliedWinCombinations.keySet()) {
            List<String> expectedList = expectedAppliedWinCombinations.get(key);
            List<String> actualList = result.getAppliedWinningCombinations().get(key);

            assertTrue(actualList.containsAll(expectedList) && expectedList.containsAll(actualList),
                    "Lists for key '" + key + "' do not have the same elements");
            assertEquals(expectedList.size(), actualList.size(), "Lists for key '" + key + "' have different sizes");
        }

        assertEquals("10x", result.getAppliedBonusSymbol());
    }
}