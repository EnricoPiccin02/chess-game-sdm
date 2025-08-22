package com.sdm.units.chessgame.gui.board.clock;

public class DefaultClockFormatter implements ClockFormatter {

    @Override
    public String formatTime(long millis) {
        long seconds = millis / 1000;
        return String.format("\t%d:%02d", seconds / 60, seconds % 60);
    }
}