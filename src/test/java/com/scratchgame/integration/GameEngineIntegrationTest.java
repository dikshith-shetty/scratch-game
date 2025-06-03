package com.scratchgame.integration;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.scratchgame.config.dto.Config;
import com.scratchgame.config.ConfigLoader;
import com.scratchgame.config.validation.ConfigValidationService;
import com.scratchgame.config.validation.DimensionsValidator;
import com.scratchgame.config.validation.SymbolValidator;
import com.scratchgame.core.GameEngine;
import com.scratchgame.core.GameResult;
import com.scratchgame.core.MatrixGenerator;
import com.scratchgame.core.RewardCalculator;
import com.scratchgame.core.winrule.WinRuleEvaluator;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineIntegrationTest {

    @Test
    void testGameEngineWithRealConfig() throws IOException {
        // Load the configuration
        ConfigValidationService validationService = new ConfigValidationService(Arrays.asList(new DimensionsValidator(), new SymbolValidator()));
        ConfigLoader configLoader = new ConfigLoader(validationService);
        Config config = configLoader.load("src/test/resources/config-3x3.json");

        // Create the GameEngine
        GameEngine gameEngine = new GameEngine(
                config,
                new MatrixGenerator(config),
                new WinRuleEvaluator(),
                new RewardCalculator()
        );

        // Play the game
        double betAmount = 100.0;
        GameResult result = gameEngine.play(betAmount);

        // Assertions to check if the process ran without errors
        assertNotNull(result.getMatrix());
        assertEquals(result.getMatrix().size(), config.getRows());
        if (!result.getMatrix().isEmpty()) {
            assertEquals(result.getMatrix().get(0).size(), config.getColumns());
        }
        assertNotNull(result.getAppliedWinningCombinations());
        assertTrue(result.getReward() >= 0);
        System.out.println("\n--- Integration Test Result ---");
        System.out.println(result);
    }
}