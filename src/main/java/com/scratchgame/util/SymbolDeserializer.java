package com.scratchgame.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scratchgame.config.dto.*;
import com.scratchgame.enums.BonusImpactType;
import com.scratchgame.enums.SymbolType;

import java.io.IOException;

public class SymbolDeserializer extends JsonDeserializer<Symbol> {

    @Override
    public Symbol deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        ObjectNode node = mapper.readTree(p);

        String typeStr = node.get("type").asText();
        SymbolType type = SymbolType.fromString(typeStr);

        // Optional: fallback if symbolId is not present
        String symbolId = node.has("symbolId") ? node.get("symbolId").asText() : null;

        switch (type) {
            case STANDARD:
                double rewardMultiplier = node.get("reward_multiplier").asDouble();
                return new StandardSymbol(symbolId, rewardMultiplier);

            case BONUS:
                BonusImpactType impact = BonusImpactType.fromString(node.get("impact").asText());

                switch (impact) {
                    case MULTIPLY_REWARD:
                        double bonusReward = node.get("reward_multiplier").asDouble();
                        return new MultiplyRewardBonus(symbolId, bonusReward);

                    case EXTRA_BONUS:
                        int extra = node.get("extra").asInt();
                        return new ExtraBonus(symbolId, extra);

                    case MISS:
                        return new MissBonus(symbolId);

                    default:
                        throw new IllegalArgumentException("Unsupported impact: " + impact);
                }

            default:
                throw new IllegalArgumentException("Unsupported type: " + typeStr);
        }
    }
}
