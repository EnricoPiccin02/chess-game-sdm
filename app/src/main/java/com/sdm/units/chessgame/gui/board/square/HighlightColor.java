package com.sdm.units.chessgame.gui.board.square;

import java.awt.Color;

public enum HighlightColor {

    SELECTABLE, CLICKED, HOVER, NONE;

    public Color getColor() {
        return switch (this) {
            case SELECTABLE -> Color.YELLOW;
            case CLICKED -> Color.RED;
            case HOVER -> Color.BLUE;
            default -> null;
        };
    }
}