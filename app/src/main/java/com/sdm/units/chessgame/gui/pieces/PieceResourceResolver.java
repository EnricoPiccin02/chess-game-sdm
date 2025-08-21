package com.sdm.units.chessgame.gui.pieces;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

@FunctionalInterface
public interface PieceResourceResolver {
    
    String resolvePath(ChessPiece piece);
}