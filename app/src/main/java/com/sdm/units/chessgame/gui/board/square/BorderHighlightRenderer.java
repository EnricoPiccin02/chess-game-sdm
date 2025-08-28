package com.sdm.units.chessgame.gui.board.square;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class BorderHighlightRenderer implements HighlightRenderer {
    
    @Override
    public void render(JComponent component, HighlightColor type) {
        if (type == HighlightColor.NONE) {
            component.setBorder(BorderFactory.createEmptyBorder());
        } else {
            component.setBorder(BorderFactory.createLineBorder(type.getColor(), 2));
        }
    }
}