package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.movement.PawnMovementStrategy;

public class Pawn extends BoardPiece {

    private ChessboardOrientation orientation;

    public Pawn(ChessPieceColor color, ChessboardOrientation orientation) {
        super(color,
            ChessPieceInfo.PAWN,
            new PawnMovementStrategy(orientation)
        );
        this.orientation = orientation;
    }

    @Override
    public ChessPiece copy() {
        return new Pawn(pieceColor(), orientation);
    }
}