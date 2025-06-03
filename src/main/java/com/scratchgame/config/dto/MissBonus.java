package com.scratchgame.config.dto;

import com.scratchgame.enums.BonusImpactType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MissBonus extends BonusSymbol {
    public MissBonus(String symbolId) {
        super(symbolId, BonusImpactType.MISS);
    }
}
