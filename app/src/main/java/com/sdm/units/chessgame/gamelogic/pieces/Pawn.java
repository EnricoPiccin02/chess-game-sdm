package com.sdm.units.chessgame.gamelogic.pieces;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.movement.PawnMovementStrategy;

public class Pawn extends BoardPiece {

    public Pawn(ChessPieceColor color, ChessboardOrientation orientation) {
        super(color,
            ChessPieceInfo.PAWN,
            new PawnMovementStrategy(orientation)
        );
    }
}