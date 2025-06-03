package com.scratchgame.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.scratchgame.config.BonusSymbolsProbability;
import com.scratchgame.config.Config;
import com.scratchgame.config.StandardSymbolProbability;
import com.scratchgame.config.Symbol;

public class MatrixGenerator {

    private final Config config;
    private final Random random = new Random();

    public MatrixGenerator(Config config) {
        this.config = config;
    }

    public List<List<Symbol>> generateMatrix() {
        int rows = config.getRows();
        int columns = config.getColumns();
        List<List<Symbol>> matrix = new ArrayList<>();
        BonusSymbolsProbability bonusProbabilities = config.getProbabilities().getBonusSymbols();

        for (int row = 0; row < rows; row++) {
            List<Symbol> currentRow = new ArrayList<>();
            for (int col = 0; col < columns; col++) {
                Symbol symbol = generateStandardSymbol(row, col);
                currentRow.add(symbol);
            }
            matrix.add(currentRow);
        }

        if (bonusProbabilities != null && bonusProbabilities.getSymbols() != null) {
            if (!matrix.isEmpty() && !matrix.get(0).isEmpty()) { // Check for empty matrix
                int randomRowIndex = random.nextInt(matrix.size());
                int randomColIndex = random.nextInt(matrix.get(0).size());
                matrix.get(randomRowIndex).set(randomColIndex, generateBonusSymbol(bonusProbabilities.getSymbols()));
            }
        }
        return matrix;
    }

    private Symbol generateStandardSymbol(int row, int col) {
        List<StandardSymbolProbability> standardSymbolsProbabilities = config.getProbabilities().getStandardSymbols();
        StandardSymbolProbability cellProbabilities = null;

        if (standardSymbolsProbabilities != null) {
            for (StandardSymbolProbability prob : standardSymbolsProbabilities) {
                if (prob.getRow() == row && prob.getColumn() == col) {
                    cellProbabilities = prob;
                    break;
                }
            }
        }

        if (cellProbabilities == null && !standardSymbolsProbabilities.isEmpty()) {
            cellProbabilities = standardSymbolsProbabilities.get(0);
        } else if (cellProbabilities == null) {
            throw new IllegalStateException("No standard symbol probabilities defined in config.");
        }

        return selectSymbol(cellProbabilities.getSymbols(), config.getSymbols());
    }

    private Symbol generateBonusSymbol(Map<String, Integer> bonusSymbolProbabilities) {
        return selectSymbol(bonusSymbolProbabilities, config.getSymbols());
    }

    private Symbol selectSymbol(Map<String, Integer> symbolProbabilities, Map<String, Symbol> allSymbols) {
        int totalProbability = symbolProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        if (totalProbability <= 0) {
            // Handle the case where totalProbability is not positive
            // You might want to throw an exception, log an error, or return a default symbol
            System.err.println("Error: Total probability is not positive: " + totalProbability);
            // Example: Returning the first symbol as a fallback (or null if appropriate)
            if (!allSymbols.isEmpty()) {
                return allSymbols.entrySet().iterator().next().getValue();
            } else {
                throw new IllegalStateException("No symbols defined in config.");
            }
        }

        int randomNum = random.nextInt(totalProbability);
        int currentSum = 0;

        for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
            currentSum += entry.getValue();
            if (randomNum < currentSum) {
                return allSymbols.get(entry.getKey());
            }
        }
        // Fallback - should ideally not happen
        return allSymbols.entrySet().iterator().next().getValue();
    }
}