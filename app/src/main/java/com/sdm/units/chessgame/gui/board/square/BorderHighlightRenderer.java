package com.sdm.units.chessgame.gui.board.square;

import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class BorderHighlightRenderer implements HighlightRenderer {
    
    @Override
    public void apply(JComponent component, Optional<HighlightColor> type) {
        type.ifPresentOrElse(
            t -> component.setBorder(BorderFactory.createLineBorder(t.getColor(), 2)),
            () -> component.setBorder(BorderFactory.createEmptyBorder())
        );
    }
}