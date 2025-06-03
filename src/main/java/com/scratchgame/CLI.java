package com.scratchgame;

import java.io.IOException;
import java.util.Arrays;

import com.scratchgame.config.dto.Config;
import com.scratchgame.config.ConfigLoader;
import com.scratchgame.config.validation.ConfigValidationService;
import com.scratchgame.config.validation.DimensionsValidator;
import com.scratchgame.config.validation.SymbolValidator;
import com.scratchgame.config.validation.WinRuleValidator;
import com.scratchgame.core.GameEngine;
import com.scratchgame.core.GameResult;
import com.scratchgame.core.MatrixGenerator;
import com.scratchgame.core.RewardCalculator;
import com.scratchgame.core.winrule.WinRuleEvaluator;


public class CLI {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java -jar <your-jar-file> --config <config.json> --betting-amount <amount>");
            System.exit(1);
        }

        String configFilePath = null;
        int bettingAmount = 0;

        for (int i = 0; i < args.length; i++) {
            if ("--config".equals(args[i])) {
                configFilePath = args[i + 1];
                i++; // Skip the next argument
            } else if ("--betting-amount".equals(args[i])) {
                try {
                    bettingAmount = Integer.parseInt(args[i + 1]);
                    i++; // Skip the next argument
                } catch (NumberFormatException e) {
                    System.out.println("Invalid betting amount. Must be a Integer: " + args[i + 1]);
                    System.exit(1);
                }
            }
        }

        if (configFilePath == null) {
            System.out.println("Invalid argument for --config. --config shouldn't be null. Please provide both --config and --betting-amount.");
        }

        if (bettingAmount <= 0) {
            System.out.println("Invalid argument for --betting-amount. --betting-amount should be greater than 0. Please provide both --config and --betting-amount.");
            System.exit(1);
        }

        // Create validators
        DimensionsValidator dimensionsValidator = new DimensionsValidator();
        SymbolValidator symbolsValidator = new SymbolValidator();
        WinRuleValidator winRuleValidator = new WinRuleValidator();
        // Create validation service
        ConfigValidationService validationService = new ConfigValidationService(Arrays.asList(dimensionsValidator, symbolsValidator,winRuleValidator));
        ConfigLoader configLoader = new ConfigLoader(validationService);

        try {
            Config config = configLoader.load(configFilePath);
            // *** Game logic starts here ***
            // Create the game engine
            GameEngine gameEngine = new GameEngine(
                    config,
                    new MatrixGenerator(config),
                    new WinRuleEvaluator(),
                    new RewardCalculator()
            );
            // Play the game
            GameResult result = gameEngine.play(bettingAmount);
            // print game result
            System.out.println(result);
            // *** Game logic ends here ***
        } catch (IOException e) {
            System.out.println("Error reading config file: " + configFilePath);
            System.exit(1);
        }
    }
}