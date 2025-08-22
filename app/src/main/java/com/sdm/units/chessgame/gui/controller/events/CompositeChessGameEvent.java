package com.sdm.units.chessgame.gui.controller.events;

import java.util.List;

import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public class CompositeChessGameEvent extends ChessGameEvent {

    private final List<ChessGameEvent> events;

    public CompositeChessGameEvent(ChessGameEvent... events) {
        super("Composite Event");
        this.events = List.of(events);
    }
    
    @Override
    public void handleOn(ChessGameView view) {
        for (ChessGameEvent event : events) {
            event.handleOn(view);
        }
    }

    public List<ChessGameEvent> events() {
        return events;
    }
}