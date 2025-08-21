package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

@FunctionalInterface
public interface PromotionPieceSelector {
    
    ChessPiece selectPromotionPiece(ChessPieceColor color);
}