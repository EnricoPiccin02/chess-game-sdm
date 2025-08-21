package com.sdm.units.chessgame.gui.controller.events;

import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public class GameMessageEvent extends ChessGameEvent {
    
    public GameMessageEvent(String message) {
        super(message);
    }

    @Override
    public void handleOn(ChessGameView view) {
        view.displayMessage(getMessage());
    }
}