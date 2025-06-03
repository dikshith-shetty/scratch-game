package com.scratchgame.core.winrule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scratchgame.config.dto.Config;
import com.scratchgame.config.dto.Symbol;
import com.scratchgame.enums.SymbolType;
import com.scratchgame.config.dto.WinCombinations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class WinRuleEvaluator {

    @Data
    @AllArgsConstructor
    private static class Coordinate {

        int row;
        int col;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coordinate that = (Coordinate) o;
            return row == that.row && col == that.col;
        }

        @Override
        public int hashCode() {
            return 31 * row + col;
        }
    }

    private final Map<Symbol, Integer> symbolCounts = new HashMap<>();
    private final Map<Symbol, List<Coordinate>> symbolPositions = new HashMap<>();
    private final Map<Coordinate, Symbol> coordinateToSymbolMap = new HashMap<>();
    private final Map<Symbol, List<WinCombinations>> appliedWinCombinations = new HashMap<>();

    @Getter
    private Symbol encounteredBonusSymbol = null; // To store the applied bonus symbol

    public Map<Symbol, List<WinCombinations>> evaluate(List<List<Symbol>> matrix, Config config) {
        initializeDataStructures();
        scanMatrix(matrix);
        evaluateWinRules(config);
        refineAppliedWinCombinations();
        return appliedWinCombinations;
    }

    private void initializeDataStructures() {
        symbolCounts.clear();
        symbolPositions.clear();
        appliedWinCombinations.clear();
        coordinateToSymbolMap.clear();
        encounteredBonusSymbol = null; // Reset bonus symbol
    }

    private void scanMatrix(List<List<Symbol>> matrix) {
        for (int row = 0; row < matrix.size(); row++) {
            for (int col = 0; col < matrix.get(row).size(); col++) {
                Symbol symbol = matrix.get(row).get(col);
                Coordinate coordinate = new Coordinate(row, col);

                symbolCounts.put(symbol, symbolCounts.getOrDefault(symbol, 0) + 1);
                symbolPositions.computeIfAbsent(symbol, k -> new ArrayList<>()).add(coordinate);
                coordinateToSymbolMap.put(coordinate, symbol);

                if (symbol.getType() != null && symbol.getType().equals(SymbolType.BONUS)) {
                    encounteredBonusSymbol = symbol;
                }
            }
        }
    }

    private void evaluateWinRules(Config config) {
        if (config.getWinCombinations() != null) {
            config.getWinCombinations().forEach((key, winCombination) -> {
                WinRuleType type = WinRuleType.fromValue(winCombination.getWhen());
                if (type != null) {
                    switch (type) {
                        case SAME_SYMBOLS:
                            evaluateSameSymbols(winCombination);
                            break;
                        case LINEAR_SYMBOLS:
                            evaluateLinearSymbols(winCombination, config.getRows(), config.getColumns());
                            break;
                        default:
                            System.out.println("Unknown win combination type: " + winCombination.getWhen());
                    }
                } else {
                    System.out.println("Unknown win combination type in config: " + winCombination.getWhen());
                }
            });
        }
    }

    private void evaluateSameSymbols(WinCombinations winCombination) {
        symbolCounts.forEach((symbol, count) -> {
            if (winCombination.getCount() != null && count >= winCombination.getCount()) {
                appliedWinCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(winCombination);
            }
        });
    }

    private void evaluateLinearSymbols(WinCombinations winCombination, int rows, int columns) {
        if (winCombination.getCoveredAreas() == null) {
            return;
        }

        for (List<String> coveredArea : winCombination.getCoveredAreas()) {
            if (coveredArea.isEmpty()) {
                continue;
            }

            Symbol firstSymbol = null;
            boolean match = true;

            for (String coord : coveredArea) {
                String[] parts = coord.split(":");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                if (row < 0 || row >= rows || col < 0 || col >= columns) {
                    match = false;
                    break;
                }

                Symbol symbol = coordinateToSymbolMap.get(new Coordinate(row, col));
                if (firstSymbol == null) {
                    firstSymbol = symbol;
                } else if (!firstSymbol.equals(symbol)) {
                    match = false;
                    break;
                }
            }

            if (match && firstSymbol != null) {
                appliedWinCombinations.computeIfAbsent(firstSymbol, k -> new ArrayList<>()).add(winCombination);
            }
        }
    }

     /**
     * Refines the map of applied win combinations by keeping only the WinCombination
     * with the highest reward multiplier within each group for a given symbol.
     * Standalone win combinations (those without a group) are always kept.
     */
    private void refineAppliedWinCombinations() {
        Map<Symbol, List<WinCombinations>> refinedMap = new HashMap<>();

        appliedWinCombinations.forEach((symbol, winsForSymbol) -> {
            Map<String, WinCombinations> bestWinPerGroup = new HashMap<>();
            List<WinCombinations> standaloneWins = new ArrayList<>();

            for (WinCombinations win : winsForSymbol) {
                if (win.getGroup() != null && !win.getGroup().isEmpty()) {
                    // If a group exists, check if this is the best multiplier so far for this group
                    bestWinPerGroup.compute(win.getGroup(), (groupName, currentBest) -> {
                        if (currentBest == null || win.getRewardMultiplier() > currentBest.getRewardMultiplier()) {
                            return win; // This win is better
                        }
                        return currentBest; // Keep the current best
                    });
                } else {
                    // If no group, it's a standalone win, always keep it
                    standaloneWins.add(win);
                }
            }

            // Collect all best wins from groups and standalone wins
            List<WinCombinations> refinedWins = new ArrayList<>(bestWinPerGroup.values());
            refinedWins.addAll(standaloneWins);

            if (!refinedWins.isEmpty()) {
                refinedMap.put(symbol, refinedWins);
            }
        });

        this.appliedWinCombinations.clear();
        this.appliedWinCombinations.putAll(refinedMap);
    }
}
