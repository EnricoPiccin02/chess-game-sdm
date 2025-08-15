package com.sdm.units.chessgame.gamelogic.move.core;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;

public interface ReversibleMove extends ChessMove {
    
    CaptureResult undoOn(Chessboard board);
}