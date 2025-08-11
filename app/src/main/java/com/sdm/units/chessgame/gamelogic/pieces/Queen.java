package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionSet;
import com.sdm.units.chessgame.gamelogic.movement.SlidingMovementStrategy;

public class Queen extends ChessPiece {

    public Queen(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.QUEEN,
            new SlidingMovementStrategy(ChessboardDirectionSet.ORTHOGONAL_AND_DIAGONAL::getDirections)
        );
    }

    @Override
    public ChessPiece copy() {
        return new Queen(pieceColor());
    }
}