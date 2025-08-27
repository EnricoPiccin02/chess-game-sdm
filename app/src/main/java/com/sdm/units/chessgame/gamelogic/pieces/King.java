package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionGroup;
import com.sdm.units.chessgame.gamelogic.movement.KingMovementStrategy;

public class King extends BoardPiece {
    
    public King(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.KING,
            new KingMovementStrategy(ChessboardDirectionGroup.ORTHOGONAL_AND_DIAGONAL::getDirections)
        );
    }
}