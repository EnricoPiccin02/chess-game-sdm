package com.sdm.units.chessgame.gui.controller.events;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public class ClockStartEvent extends ChessGameEvent {
    
    private final ChessPieceColor color;

    public ClockStartEvent(ChessPieceColor color) {
        super("Clock Start Event");
        this.color = color;
    }

    @Override
    public void handleOn(ChessGameView view) {
        view.startClock(color);
    }
}