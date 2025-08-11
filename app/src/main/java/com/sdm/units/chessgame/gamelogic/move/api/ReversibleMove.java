package com.sdm.units.chessgame.gamelogic.move.api;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;

public interface ReversibleMove extends ChessMove {
    
    void undoOn(Chessboard board);
}