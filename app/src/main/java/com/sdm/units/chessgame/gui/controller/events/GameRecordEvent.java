package com.sdm.units.chessgame.gui.controller.events;

import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public class GameRecordEvent extends ChessGameEvent {
    
    public GameRecordEvent(String message) {
        super(message);
    }

    @Override
    public void handleOn(ChessGameView view) {
        view.recordMessage(getMessage());
    }
}