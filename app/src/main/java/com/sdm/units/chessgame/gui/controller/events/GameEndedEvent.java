package com.sdm.units.chessgame.gui.controller.events;

import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public class GameEndedEvent extends ChessGameEvent {
    
    public GameEndedEvent(String message) {
        super(message);
    }

    @Override
    public void handleOn(ChessGameView view) {
        view.displayMessage(getMessage());
        view.close();
    }
}