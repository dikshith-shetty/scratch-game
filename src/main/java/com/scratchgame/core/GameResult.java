package com.scratchgame.core;

import java.util.List;
import java.util.Map;

import com.scratchgame.config.dto.Symbol;
import com.scratchgame.config.dto.WinCombinations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResult {
    private final List<List<String>> matrix; // Store symbol IDs in the matrix
    private final double reward;
    private final Map<String, List<String>> appliedWinningCombinations; // Store symbol ID and list of win combination names
    private final String appliedBonusSymbol;

    public GameResult(List<List<Symbol>> matrix, Map<Symbol, List<WinCombinations>> appliedWinCombinations, double reward, Symbol bonusSymbol) {
        this.matrix = matrix.stream()
                .map(row -> row.stream().map(Symbol::getSymbolId).toList())
                .toList();
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinCombinations.entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                        entry -> entry.getKey().getSymbolId(),
                        entry -> entry.getValue().stream().map(WinCombinations::getName).toList()
                ));
        this.appliedBonusSymbol = bonusSymbol != null ? bonusSymbol.getSymbolId() : null;
    }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        boolean hasPrev = false;
        if (matrix != null) {
            sb.append("    \"matrix\" : [\n");
            for (int i = 0; i < matrix.size(); i++) {
                sb.append("       [");
                List<String> row = matrix.get(i);
                for (int j = 0; j < row.size(); j++) {
                    sb.append("\"").append(row.get(j)).append("\"");
                    if (j < row.size() - 1) sb.append(", ");
                }
                sb.append("]");
                if (i < matrix.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append("    ]");
            hasPrev = true;
        }

        if (hasPrev) sb.append(",\n");
        sb.append("    \"reward\" : ").append(String.valueOf(reward).split("\\.")[0]);
        hasPrev = true;

        if (appliedWinningCombinations != null && !appliedWinningCombinations.isEmpty()) {
            if (hasPrev) sb.append(",\n");
            sb.append("    \"applied_winning_combinations\" : {\n");
            int i = 0;
            for (Map.Entry<String, List<String>> entry : appliedWinningCombinations.entrySet()) {
                sb.append("        \"").append(entry.getKey()).append("\" : [");
                List<String> wins = entry.getValue();
                for (int j = 0; j < wins.size(); j++) {
                    sb.append("\"").append(wins.get(j)).append("\"");
                    if (j < wins.size() - 1) sb.append(", ");
                }
                sb.append("]");
                if (i < appliedWinningCombinations.size() - 1) sb.append(",");
                sb.append("\n");
                i++;
            }
            sb.append("    }");
            hasPrev = true;
            if (appliedBonusSymbol != null) {
                if (hasPrev) sb.append(",\n");
                sb.append("    \"applied_bonus_symbol\" : \"").append(appliedBonusSymbol).append("\"");
            }
        }
        sb.append("\n}");
        return sb.toString();
    }
}