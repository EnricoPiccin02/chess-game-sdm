package com.sdm.units.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.pieces.ChessPiece;

import java.util.Optional;

public record ChessMove(ChessboardPosition fromPosition,
                   ChessboardPosition toPosition,
                   Optional<ChessPiece> piece) {
    public ChessboardPosition getStart() {
        return fromPosition;
    }

    public ChessboardPosition getTarget() {
        return toPosition;
    }
}
