package com.sdm.units.chessgame.gui.controller.events;

import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public class GameStartedEvent extends ChessGameEvent {
    
    public GameStartedEvent(String message) {
        super(message);
    }

    @Override
    public void handleOn(ChessGameView view) {
        view.reset();
        view.initialize();
        view.displayMessage(getMessage());
    }
}