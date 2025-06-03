package com.scratchgame.core;

import com.scratchgame.config.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MatrixGeneratorTest {

    @Test
    void testGenerateMatrix_standardSymbols() {
        // Mock Config
        Config config = Mockito.mock(Config.class);
        when(config.getRows()).thenReturn(2);
        when(config.getColumns()).thenReturn(2);

        // Mock Symbols
        Map<String, Symbol> symbols = new HashMap<>();
        symbols.put("A", new StandardSymbol("A", 1.0));
        symbols.put("B", new StandardSymbol("B", 0.5));
        when(config.getSymbols()).thenReturn(symbols);

        // Mock Standard Probabilities
        List<StandardSymbolProbability> standardProbabilities = Arrays.asList(
                createStandardProbability(0, 0, Map.of("A", 100)),
                createStandardProbability(0, 1, Map.of("B", 100)),
                createStandardProbability(1, 0, Map.of("A", 50, "B", 50)),
                createStandardProbability(1, 1, Map.of("A", 20, "B", 80))
        );
        Probabilities probabilities = Mockito.mock(Probabilities.class);
        when(probabilities.getStandardSymbols()).thenReturn(standardProbabilities);
        when(config.getProbabilities()).thenReturn(probabilities);
        when(probabilities.getBonusSymbols()).thenReturn(new BonusSymbolsProbability(new HashMap<>())); // No bonus symbols for this test

        MatrixGenerator generator = new MatrixGenerator(config);
        List<List<Symbol>> matrix = generator.generateMatrix();

        assertEquals(2, matrix.size());
        assertEquals(2, matrix.get(0).size());
        assertNotNull(matrix.get(0).get(0));
        assertNotNull(matrix.get(0).get(1));
        assertNotNull(matrix.get(1).get(0));
        assertNotNull(matrix.get(1).get(1));

        // We can't predict the exact symbols due to randomness, but we can check the sizes
    }

    @Test
    void testGenerateMatrix_withBonusSymbols() {
        // Mock Config
        Config config = Mockito.mock(Config.class);
        when(config.getRows()).thenReturn(1);
        when(config.getColumns()).thenReturn(1);

        // Mock Symbols
        Map<String, Symbol> symbols = new HashMap<>();
        symbols.put("A", new StandardSymbol("A", 1.0));
        symbols.put("BONUS", new MultiplyRewardBonus("BONUS", 2.0));
        when(config.getSymbols()).thenReturn(symbols);

        // Mock Standard Probabilities
        List<StandardSymbolProbability> standardProbabilities = Arrays.asList(
                createStandardProbability(0, 0, Map.of("A", 100))
        );
        Probabilities probabilities = Mockito.mock(Probabilities.class);
        when(probabilities.getStandardSymbols()).thenReturn(standardProbabilities);

        // Mock Bonus Probabilities
        Map<String, Integer> bonusSymbolsProb = new HashMap<>();
        bonusSymbolsProb.put("BONUS", 100);
        when(probabilities.getBonusSymbols()).thenReturn(new BonusSymbolsProbability(bonusSymbolsProb));
        when(config.getProbabilities()).thenReturn(probabilities);

        MatrixGenerator generator = new MatrixGenerator(config);
        List<List<Symbol>> matrix = generator.generateMatrix();

        assertEquals(1, matrix.size());
        assertEquals(1, matrix.get(0).size());
        assertNotNull(matrix.get(0).get(0));
        // Due to the 10% probability, we can't assert the exact symbol, but we can check it's not null
    }

    @Test
    void testGenerateMatrix_noStandardProbabilities() {
        Config config = Mockito.mock(Config.class);
        when(config.getRows()).thenReturn(1);
        when(config.getColumns()).thenReturn(1);
        when(config.getProbabilities()).thenReturn(Mockito.mock(Probabilities.class));
        when(config.getProbabilities().getStandardSymbols()).thenReturn(List.of());
        when(config.getSymbols()).thenReturn(Map.of("A", new StandardSymbol("A", 1.0)));

        MatrixGenerator generator = new MatrixGenerator(config);
        assertThrows(IllegalStateException.class, generator::generateMatrix);
    }

    private StandardSymbolProbability createStandardProbability(int row, int col, Map<String, Integer> symbols) {
        StandardSymbolProbability prob = new StandardSymbolProbability();
        prob.setRow(row);
        prob.setColumn(col);
        prob.setSymbols(symbols);
        return prob;
    }
}