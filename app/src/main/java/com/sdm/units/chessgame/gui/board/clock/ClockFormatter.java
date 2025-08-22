package com.sdm.units.chessgame.gui.board.clock;

@FunctionalInterface
public interface ClockFormatter {

    String formatTime(long millis);
}