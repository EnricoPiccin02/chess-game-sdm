package com.sdm.units.chessgame.gui.controller.events;

public interface ChessGameEventPublisher {

    void addChessGameEventListener(ChessGameEventListener listener);

    void removeChessGameEventListener(ChessGameEventListener listener);
    
    void fireEvent(ChessGameEvent event);
}