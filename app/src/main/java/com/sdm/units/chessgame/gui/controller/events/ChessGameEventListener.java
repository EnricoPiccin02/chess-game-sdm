package com.sdm.units.chessgame.gui.controller.events;

@FunctionalInterface
public interface ChessGameEventListener {
    
    void onChessGameEvent(ChessGameEvent event);
}