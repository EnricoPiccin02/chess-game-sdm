package com.sdm.units.chessgame.gui.board.clock;

import javax.swing.JComponent;

public interface ChessClockView {

    void start();
    
    void stop();
    
    void reset();

    void updateTime(String time);

    String getTime();

    JComponent asComponent();
}