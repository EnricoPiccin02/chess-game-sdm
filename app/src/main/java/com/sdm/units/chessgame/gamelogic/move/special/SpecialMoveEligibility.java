package com.sdm.units.chessgame.gamelogic.move.special;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;

@FunctionalInterface
public interface SpecialMoveEligibility<SpecialMoveCandidate> {
    
    boolean canExecute(Chessboard board, SpecialMoveCandidate candidate, ChessboardOrientation orientation);
}