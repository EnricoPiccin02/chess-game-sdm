package com.sdm.units.chessgame.gui.board.square;

public enum SquareSize {
    
    SQUARE_WIDTH, SQUARE_HEIGHT;

    public int getSize() {
        return switch (this) {
            case SQUARE_WIDTH -> 80;
            case SQUARE_HEIGHT -> 80;
        };
    }
}