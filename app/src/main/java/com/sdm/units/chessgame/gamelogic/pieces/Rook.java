package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionGroup;
import com.sdm.units.chessgame.gamelogic.movement.SlidingMovementStrategy;

public class Rook extends BoardPiece {

    public Rook(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.ROOK,
            new SlidingMovementStrategy(ChessboardDirectionGroup.ORTHOGONAL::getDirections)
        );
    }
}