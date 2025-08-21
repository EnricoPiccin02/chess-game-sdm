package com.sdm.units.chessgame.gui.controller.events;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public class UpdateChessboardEvent extends ChessGameEvent {

    private final Chessboard board;

    public UpdateChessboardEvent(Chessboard board) {
        super("Update Chessboard Event");
        this.board = board;
    }
    
    @Override
    public void handleOn(ChessGameView view) {
        view.setChessboard(board);
    }
}