package com.sdm.units.chessgame.gui.pieces;

public class ChessViewConfigurator {
    
    public static ChessPieceViewRegistry createViewRegistry() {
        return new ChessPieceViewRegistry(
            new BatikSvgRenderer(),
            new DefaultPieceResourceResolver()
        );
    }
}