package com.sdm.units.chessgame.gui.board.view;

import javax.swing.JComponent;

public interface GameToolbarView {

    void restart();

    void undo();

    void exit();

    JComponent asComponent();
}