package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.CompositeDirectionSet;
import com.sdm.units.chessgame.gamelogic.movement.KnightMovementStrategy;

public class Knight extends ChessPiece {
    
    public Knight(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.KNIGHT,
            new KnightMovementStrategy(CompositeDirectionSet.L_SHAPED::getCompositeDirections)
        );
    }
}