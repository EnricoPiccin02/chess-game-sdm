package com.sdm.units.chessgame.gui.board.square;

public interface SquareRenderer {
     
    void highlight(HighlightColor type);

    void clearHighlight();

    void refresh();
}