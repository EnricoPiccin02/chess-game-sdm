package com.sdm.units.chessgame.gamelogic;

public enum ChessPieceInfo {
    
    PAWN("Pawn", 1, 8),
    KNIGHT("Knight", 3, 2),
    BISHOP("Bishop", 3, 2),
    ROOK("Rook", 5, 2),
    QUEEN("Queen", 9, 1),
    KING("King", -1, 1);
    
    private final String name;
    private final int value;
    private final int numberOfAllowedPieces;

    ChessPieceInfo(String name, int value, int numberOfAllowedPieces) {
        this.name = name;
        this.value = value;
        this.numberOfAllowedPieces = numberOfAllowedPieces;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getPieceValue() {
        return this.value;
    }

    public int getNumberOfAllowedPieces() {
        return this.numberOfAllowedPieces;
    }
}