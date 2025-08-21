package com.sdm.units.chessgame.gui.controller.events;

import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public abstract class ChessGameEvent {

    private final String message;

    public ChessGameEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public abstract void handleOn(ChessGameView view);
}