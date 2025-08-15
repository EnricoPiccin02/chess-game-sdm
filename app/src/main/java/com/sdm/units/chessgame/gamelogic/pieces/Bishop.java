package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionSet;
import com.sdm.units.chessgame.gamelogic.movement.SlidingMovementStrategy;

public class Bishop extends BoardPiece {
    
    public Bishop(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.BISHOP,
            new SlidingMovementStrategy(ChessboardDirectionSet.DIAGONAL::getDirections)
        );
    }

    @Override
    public ChessPiece copy() {
        return new Bishop(pieceColor());
    }
}