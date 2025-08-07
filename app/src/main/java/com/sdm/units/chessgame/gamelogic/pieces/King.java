package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionSet;
import com.sdm.units.chessgame.gamelogic.movement.KingMovementStrategy;

public class King extends ChessPiece {
    
    public King(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.KING,
            new KingMovementStrategy(ChessboardDirectionSet.ORTHOGONAL_AND_DIAGONAL::getDirections)
        );
    }
}