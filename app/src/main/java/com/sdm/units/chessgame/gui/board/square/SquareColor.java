package com.sdm.units.chessgame.gui.board.square;

import java.awt.Color;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public enum SquareColor {
    
    LIGHT(new Color(247, 208, 164)),
    DARK(new Color(199, 142, 83));

    private final Color color;

    private SquareColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static SquareColor fromPosition(ChessboardPosition position) {
        return (position.fileIndex() + position.rankIndex()) % 2 != 0 ? LIGHT : DARK;
    }
}