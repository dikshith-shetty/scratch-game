package com.scratchgame.config.dto;

import com.scratchgame.enums.SymbolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Symbol {
    private String symbolId;
    private SymbolType type;
}