package com.scratchgame.core.winrule;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.scratchgame.config.Config;
import com.scratchgame.config.Symbol;
import com.scratchgame.config.WinCombinations;

public class WinRuleEvaluatorTest {

    private Symbol symbolA = new Symbol("A", 1.0, null, null, 0);
    private Symbol symbolB = new Symbol("B", 0.5, null, null, 0);
    private Symbol symbolC = new Symbol("C", 0.8, null, null, 0);
    private Symbol symbolD = new Symbol("D", 1.2, null, null, 0);
    private Symbol symbolE = new Symbol("E", 1.0, null, null, 0);
    private Symbol symbolF = new Symbol("F", 0.7, null, null, 0);
    private Symbol symbolG = new Symbol("G", 1.1, null, null, 0);
    private Symbol symbolH = new Symbol("H", 0.9, null, null, 0);
    private Symbol symbolI = new Symbol("I", 1.5, null, null, 0);

    @Test
    void testEvaluate_sameSymbolsWin() {
        List<List<Symbol>> matrix = Arrays.asList(
                Arrays.asList(symbolA, symbolB, symbolA),
                Arrays.asList(symbolC, symbolA, symbolD),
                Arrays.asList(symbolA, symbolE, symbolF)
        );

        WinCombinations winCombination = new WinCombinations("same_symbol_4_times", 2.0, "same_symbols", 4, "same", null);
        Map<String, WinCombinations> winCombinationsConfig = Map.of("same_symbol_4_times", winCombination);

        Config config = Mockito.mock(Config.class);
        when(config.getWinCombinations()).thenReturn(winCombinationsConfig);

        WinRuleEvaluator evaluator = new WinRuleEvaluator();
        Map<Symbol, List<WinCombinations>> result = evaluator.evaluate(matrix, config);

        assertEquals(1, result.size());
        assertEquals(1, result.get(symbolA).size());
        assertEquals(winCombination, result.get(symbolA).get(0));
    }

    @Test
    void testEvaluate_noSameSymbolsWin() {
        List<List<Symbol>> matrix = Arrays.asList(
                Arrays.asList(symbolA, symbolB, symbolC),
                Arrays.asList(symbolD, symbolE, symbolF),
                Arrays.asList(symbolG, symbolH, symbolI)
        );

        WinCombinations winCombination = new WinCombinations("same_symbol_3_times", 1.0, "same_symbols", 3, "same", null);
        Map<String, WinCombinations> winCombinationsConfig = Map.of("same_symbol_3_times", winCombination);

        Config config = Mockito.mock(Config.class);
        when(config.getWinCombinations()).thenReturn(winCombinationsConfig);

        WinRuleEvaluator evaluator = new WinRuleEvaluator();
        Map<Symbol, List<WinCombinations>> result = evaluator.evaluate(matrix, config);

        assertEquals(0, result.size());
    }

    @Test
    void testEvaluate_linearSymbolsWinHorizontal() {
        List<List<Symbol>> matrix = Arrays.asList(
                Arrays.asList(symbolA, symbolA, symbolA),
                Arrays.asList(symbolB, symbolC, symbolD),
                Arrays.asList(symbolE, symbolF, symbolG)
        );

        WinCombinations winCombination = new WinCombinations("horizontal_line_3", 3.0, "linear_symbols", null, "line", List.of(List.of("0:0", "0:1", "0:2")));
        Map<String, WinCombinations> winCombinationsConfig = Map.of("horizontal_line_3", winCombination);

        Config config = Mockito.mock(Config.class);
        when(config.getWinCombinations()).thenReturn(winCombinationsConfig);
        when(config.getColumns()).thenReturn(3);
        when(config.getRows()).thenReturn(3);

        WinRuleEvaluator evaluator = new WinRuleEvaluator();
        Map<Symbol, List<WinCombinations>> result = evaluator.evaluate(matrix, config);

        assertEquals(1, result.size());
        assertEquals(1, result.get(symbolA).size());
        assertEquals(winCombination, result.get(symbolA).get(0));
    }

    @Test
    void testEvaluate_noLinearSymbolsWin() {
        List<List<Symbol>> matrix = Arrays.asList(
                Arrays.asList(symbolA, symbolB, symbolC),
                Arrays.asList(symbolA, symbolB, symbolC),
                Arrays.asList(symbolA, symbolB, symbolC)
        );

        WinCombinations winCombination = new WinCombinations("horizontal_line_3", 3.0, "linear_symbols", null, "line", List.of(List.of("0:0", "0:1", "0:2")));
        Map<String, WinCombinations> winCombinationsConfig = Map.of("horizontal_line_3", winCombination);

        Config config = Mockito.mock(Config.class);
        when(config.getWinCombinations()).thenReturn(winCombinationsConfig);
        when(config.getColumns()).thenReturn(3);
        when(config.getRows()).thenReturn(3);

        WinRuleEvaluator evaluator = new WinRuleEvaluator();
        Map<Symbol, List<WinCombinations>> result = evaluator.evaluate(matrix, config);

        assertEquals(0, result.size());
    }

    @Test
    void testEvaluate_linearSymbolsWinVertical() {
        List<List<Symbol>> matrix = Arrays.asList(
                Arrays.asList(symbolA, symbolB, symbolC),
                Arrays.asList(symbolA, symbolD, symbolE),
                Arrays.asList(symbolA, symbolF, symbolG)
        );

        WinCombinations winCombination = new WinCombinations("vertical_line_3", 4.0, "linear_symbols", null, "line", List.of(List.of("0:0", "1:0", "2:0")));
        Map<String, WinCombinations> winCombinationsConfig = Map.of("vertical_line_3", winCombination);

        Config config = Mockito.mock(Config.class);
        when(config.getWinCombinations()).thenReturn(winCombinationsConfig);
        when(config.getColumns()).thenReturn(3);
        when(config.getRows()).thenReturn(3);

        WinRuleEvaluator evaluator = new WinRuleEvaluator();
        Map<Symbol, List<WinCombinations>> result = evaluator.evaluate(matrix, config);

        assertEquals(1, result.size());
        assertEquals(1, result.get(symbolA).size());
        assertEquals(winCombination, result.get(symbolA).get(0));
    }

    @Test
    void testEvaluate_multipleWinsForSameSymbol() {
        List<List<Symbol>> matrix = Arrays.asList(
                Arrays.asList(symbolA, symbolA, symbolA),
                Arrays.asList(symbolA, symbolB, symbolC),
                Arrays.asList(symbolA, symbolD, symbolE)
        );

        WinCombinations sameSymbolsWin = new WinCombinations("same_symbol_3", 2.0, "same_symbols", 3, "same", null);
        WinCombinations verticalLineWin = new WinCombinations("vertical_line_3", 3.0, "linear_symbols", null, "line", List.of(List.of("0:0", "1:0", "2:0")));
        WinCombinations horizontalLineWin = new WinCombinations("horizontal_line_3", 4.0, "linear_symbols", null, "line", List.of(List.of("0:0", "0:1", "0:2")));

        Map<String, WinCombinations> winCombinationsConfig = Map.of(
                "same_symbol_3", sameSymbolsWin,
                "vertical_line_3", verticalLineWin,
                "horizontal_line_3", horizontalLineWin
        );

        Config config = Mockito.mock(Config.class);
        when(config.getWinCombinations()).thenReturn(winCombinationsConfig);
        when(config.getColumns()).thenReturn(3);
        when(config.getRows()).thenReturn(3);

        WinRuleEvaluator evaluator = new WinRuleEvaluator();
        Map<Symbol, List<WinCombinations>> result = evaluator.evaluate(matrix, config);

        assertEquals(1, result.size());
        assertEquals(2, result.get(symbolA).size());
        assertTrue(result.get(symbolA).contains(sameSymbolsWin));
        assertTrue(result.get(symbolA).contains(horizontalLineWin));
    }
}