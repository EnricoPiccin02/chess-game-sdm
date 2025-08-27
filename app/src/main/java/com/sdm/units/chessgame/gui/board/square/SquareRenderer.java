package com.sdm.units.chessgame.gui.board.square;

public interface SquareRenderer {
     
    void highlight(HighlightColor color);

    void clearHighlight();

    void refresh();
}