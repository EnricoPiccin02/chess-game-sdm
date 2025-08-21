package com.sdm.units.chessgame.gui.board.square;

import java.awt.Color;

public enum HighlightColor {
    
    SELECTED, LEGAL_DESTINATION, CLICKED, HOVER;

    public Color getColor() {
        return switch (this) {
            case SELECTED -> Color.YELLOW;
            case LEGAL_DESTINATION -> Color.GREEN;
            case CLICKED -> Color.RED;
            case HOVER -> Color.BLUE;
        };
    }
}