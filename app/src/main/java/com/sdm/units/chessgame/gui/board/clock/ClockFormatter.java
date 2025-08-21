package com.sdm.units.chessgame.gui.board.clock;

import java.util.function.Consumer;

public interface ClockFormatter {
    
    void setDisplayListener(Consumer<String> displayUpdater);

    void updateDisplay(long millis);
}