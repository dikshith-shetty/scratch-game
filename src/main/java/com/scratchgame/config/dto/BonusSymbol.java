package com.scratchgame.config.dto;

import com.scratchgame.enums.BonusImpactType;
import com.scratchgame.enums.SymbolType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BonusSymbol extends Symbol {
    private BonusImpactType impact;

    public BonusSymbol(String symbolId, BonusImpactType impact) {
        super(symbolId, SymbolType.BONUS);
        this.impact = impact;
    }
}