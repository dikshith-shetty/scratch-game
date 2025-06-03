package com.scratchgame.config.dto;

import com.scratchgame.enums.BonusImpactType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraBonus extends BonusSymbol {
    private int extra;

    public ExtraBonus(String symbolId, int extra) {
        super(symbolId, BonusImpactType.EXTRA_BONUS);
        this.extra = extra;
    }
}
