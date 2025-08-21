package com.sdm.units.chessgame.gui.board.clock;

import java.util.function.Consumer;

public class DefaultClockFormatter implements ClockFormatter {

    private Consumer<String> displayUpdater;

    @Override
    public void setDisplayListener(Consumer<String> displayUpdater) {
        this.displayUpdater = displayUpdater;
    }

    @Override
    public void updateDisplay(long millis) {
        if (displayUpdater != null) {
            displayUpdater.accept(formatTime(millis));
        }
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        return String.format("\t%d:%02d", seconds / 60, seconds % 60);
    }
}