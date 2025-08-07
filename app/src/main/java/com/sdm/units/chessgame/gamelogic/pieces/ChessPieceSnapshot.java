package com.sdm.units.chessgame.gamelogic.pieces;

public class ChessPieceSnapshot {

    private final ChessPiece piece;
    private final boolean wasMoved;

    public ChessPieceSnapshot(ChessPiece piece, boolean wasMoved) {
        this.piece = piece;
        this.wasMoved = wasMoved;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public boolean wasMoved() {
        return wasMoved;
    }
}