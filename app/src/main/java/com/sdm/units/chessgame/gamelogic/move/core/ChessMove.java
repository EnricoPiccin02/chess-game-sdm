package com.sdm.units.chessgame.gamelogic.move.core;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;

public interface ChessMove extends PrimaryMoveComponentProvider {
    
    CaptureResult executeOn(Chessboard board);
}