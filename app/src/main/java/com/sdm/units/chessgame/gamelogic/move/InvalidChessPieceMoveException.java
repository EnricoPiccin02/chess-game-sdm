package com.sdm.units.chessgame.gamelogic.move;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class InvalidChessPieceMoveException extends RuntimeException {

    private InvalidChessPieceMoveException(String message) {
        super(message);
    }

    public static InvalidChessPieceMoveException illegalMove(ChessboardPosition from, ChessboardPosition to) {
        return new InvalidChessPieceMoveException("Illegal move from " + from + " to " + to + "!");
    }

    public static InvalidChessPieceMoveException undoableMove() {
        return new InvalidChessPieceMoveException("There is no move to undo!");
    }
}