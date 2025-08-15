package com.sdm.units.chessgame.gamelogic.move.core;

import java.util.Optional;
import java.util.OptionalInt;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public record CaptureResult(Optional<ChessPiece> captured) {

    public OptionalInt pieceValue() {
        return captured.isPresent()
            ? OptionalInt.of(captured.get().pieceInfo().getPieceValue())
            : OptionalInt.empty();
    }

    public static CaptureResult none() {
        return new CaptureResult(Optional.empty());
    }

    public boolean isCapture() {
        return captured.isPresent();
    }
}