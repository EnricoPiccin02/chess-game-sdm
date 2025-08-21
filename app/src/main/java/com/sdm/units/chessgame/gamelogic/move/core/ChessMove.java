package com.sdm.units.chessgame.gamelogic.move.core;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;

public interface ChessMove extends PrimaryMoveComponentProvider {
    
    CaptureResult executeOn(Chessboard board);
}