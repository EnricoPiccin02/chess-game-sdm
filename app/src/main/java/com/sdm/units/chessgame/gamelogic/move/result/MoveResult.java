package com.sdm.units.chessgame.gamelogic.move.result;

import java.util.Optional;
import java.util.OptionalInt;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public record MoveResult(ReversibleMove move, CaptureResult captureResult) {

    public static MoveResult of(ReversibleMove move, CaptureResult captureResult) {
        return new MoveResult(move, captureResult);
    }

    public static MoveResult noCapture(ReversibleMove move) {
        return new MoveResult(move, CaptureResult.none());
    }

    public boolean isCapture() {
        return captureResult.isCapture();
    }

    public Optional<ChessPiece> capturedPiece() {
        return captureResult.captured();
    }

    public OptionalInt capturedPieceValue() {
        return captureResult.pieceValue();
    }
}