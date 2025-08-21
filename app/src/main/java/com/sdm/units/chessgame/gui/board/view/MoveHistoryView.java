package com.sdm.units.chessgame.gui.board.view;

import javax.swing.JComponent;

public interface MoveHistoryView {

    String getText();

    void append(String msg);

    void clear();

    JComponent asComponent();
}