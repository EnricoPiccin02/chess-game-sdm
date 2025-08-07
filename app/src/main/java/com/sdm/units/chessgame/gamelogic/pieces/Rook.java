package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionSet;
import com.sdm.units.chessgame.gamelogic.movement.SlidingMovementStrategy;

public class Rook extends ChessPiece {

    public Rook(ChessPieceColor color) {
        super(color,
            ChessPieceInfo.ROOK,
            new SlidingMovementStrategy(ChessboardDirectionSet.ORTHOGONAL::getDirections)
        );
    }
}