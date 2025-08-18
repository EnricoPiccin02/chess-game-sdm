package com.sdm.units.chessgame.gamelogic.move.result;

import java.util.Optional;
import java.util.OptionalInt;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public record CaptureResult(Optional<ChessPiece> captured) {

    public OptionalInt pieceValue() {
        return captured.map(c -> OptionalInt.of(c.pieceInfo().getPieceValue()))
            .orElseGet(OptionalInt::empty);
    }

    public static CaptureResult none() {
        return new CaptureResult(Optional.empty());
    }

    public boolean isCapture() {
        return captured.isPresent();
    }
}