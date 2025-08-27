package com.sdm.units.chessgame.gui.pieces;

public class ChessViewConfigurator {
    
    public static PieceViewFactory createPieceViewFactory() {
        return new ChessPieceViewRegistry(
            new BatikSvgRenderer(),
            new DefaultPieceResourceResolver()
        );
    }
}