package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.CompositeMovementPattern;
import com.sdm.units.chessgame.gamelogic.movement.KnightMovementStrategy;

public class Knight extends BoardPiece {
    
    public Knight(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.KNIGHT,
            new KnightMovementStrategy(CompositeMovementPattern.L_SHAPED::getCompositeDirections)
        );
    }
}