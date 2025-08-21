package com.sdm.units.chessgame.gamelogic.move.core;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;

public interface ReversibleMove extends ChessMove {
    
    CaptureResult undoOn(Chessboard board);
}