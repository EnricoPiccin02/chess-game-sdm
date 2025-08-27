package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionGroup;
import com.sdm.units.chessgame.gamelogic.movement.SlidingMovementStrategy;

public class Queen extends BoardPiece {

    public Queen(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.QUEEN,
            new SlidingMovementStrategy(ChessboardDirectionGroup.ORTHOGONAL_AND_DIAGONAL::getDirections)
        );
    }
}