package com.sdm.units.chessgame.gui.board.square;

import javax.swing.JComponent;

@FunctionalInterface
public interface HighlightRenderer {
    
    void render(JComponent component, HighlightColor type);
}