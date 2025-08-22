package com.sdm.units.chessgame.gui.board.clock;

public interface ChessClockListener {
    
    void onTimeUpdated(long remainingMillis);
    
    void onTimeExpired();
}