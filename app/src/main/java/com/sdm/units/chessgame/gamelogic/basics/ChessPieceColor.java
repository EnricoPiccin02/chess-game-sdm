package com.sdm.units.chessgame.gamelogic.basics;

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

    public static ChessPieceColor getOppositeColor(ChessPieceColor current) {
        return current == WHITE ? BLACK : WHITE;
    }

    public abstract Color getEncodedColor();
}