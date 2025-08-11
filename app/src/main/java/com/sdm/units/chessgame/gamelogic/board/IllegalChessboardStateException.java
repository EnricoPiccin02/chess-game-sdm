package com.sdm.units.chessgame.gamelogic.board;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class IllegalChessboardStateException extends RuntimeException {

    private IllegalChessboardStateException(String message) {
        super(message);
    }

    public static IllegalChessboardStateException missingPiece(ChessboardPosition position) {
        return new IllegalChessboardStateException("No piece at position: " + position);
    }

    public static IllegalChessboardStateException missingKing(ChessPieceColor color) {
        return new IllegalChessboardStateException("No " + ChessPieceInfo.KING + " for " + color);
    }
}