package com.scratchgame.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scratchgame.config.dto.Symbol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SymbolMapDeserializer extends JsonDeserializer<Map<String, Symbol>> {

    private final ObjectMapper symbolMapper;

    public SymbolMapDeserializer() {
        this.symbolMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Symbol.class, new SymbolDeserializer());
        symbolMapper.registerModule(module);
    }

    @Override
    public Map<String, Symbol> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        ObjectNode node = p.getCodec().readTree(p);
        Map<String, Symbol> symbolMap = new HashMap<>();

        for (Map.Entry<String, JsonNode> entry : node.properties()) {
            String symbolId = entry.getKey();
            JsonNode symbolData = entry.getValue();

            // inject symbolId into the data tree
            ((ObjectNode) symbolData).put("symbolId", symbolId);

            Symbol symbol = symbolMapper.treeToValue(symbolData, Symbol.class);
            symbolMap.put(symbolId, symbol);
        }

        return symbolMap;
    }
}
