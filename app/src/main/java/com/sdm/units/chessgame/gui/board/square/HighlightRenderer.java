package com.sdm.units.chessgame.gui.board.square;

import java.util.Optional;

import javax.swing.JComponent;

@FunctionalInterface
public interface HighlightRenderer {
    
    void apply(JComponent component, Optional<HighlightColor> type);
}