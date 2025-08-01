package com.sdm.units.chessgame.gamelogic.domain;

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

    public ChessPieceColor opponent() {
        return this == WHITE ? BLACK : WHITE;
    }

    public abstract Color getEncodedColor();
}