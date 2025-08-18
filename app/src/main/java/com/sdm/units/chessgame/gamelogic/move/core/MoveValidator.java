package com.sdm.units.chessgame.gamelogic.move.core;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface MoveValidator {
    
    Optional<ReversibleMove> validateAndCreate(Chessboard board, ChessboardPosition from, ChessboardPosition to, ChessboardOrientation orientation);
}