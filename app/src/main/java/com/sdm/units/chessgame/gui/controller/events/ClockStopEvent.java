package com.sdm.units.chessgame.gui.controller.events;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public class ClockStopEvent extends ChessGameEvent {
    
    private final ChessPieceColor color;

    public ClockStopEvent(ChessPieceColor color) {
        super("Clock Stop Event");
        this.color = color;
    }

    @Override
    public void handleOn(ChessGameView view) {
        view.stopClock(color);
    }
}