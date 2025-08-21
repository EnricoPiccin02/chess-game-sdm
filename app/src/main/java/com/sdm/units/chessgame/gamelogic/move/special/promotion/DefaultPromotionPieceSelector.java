package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;

public class DefaultPromotionPieceSelector implements PromotionPieceSelector {
    
    @Override
    public ChessPiece selectPromotionPiece(ChessPieceColor color) {
        return new Queen(color);
    }
}