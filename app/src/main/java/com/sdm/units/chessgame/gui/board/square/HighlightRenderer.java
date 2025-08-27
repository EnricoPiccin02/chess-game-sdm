package com.sdm.units.chessgame.gui.board.square;

import javax.swing.JComponent;

@FunctionalInterface
public interface HighlightRenderer {
    
    void apply(JComponent component, HighlightColor type);
}