package com.sdm.units.chessgame.gamelogic;

import java.awt.Color;

public enum ChessPieceColor {
    
    WHITE("white") {
        @Override
        public Color getEncodedColor() {
            return Color.WHITE;
        }
    },
    BLACK("black") {
        @Override
        public Color getEncodedColor() {
            return Color.BLACK;
        }
    };
    
    public final String name;

    ChessPieceColor(String name) {
        this.name = name;
    }

    public abstract Color getEncodedColor();
}