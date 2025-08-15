package com.sdm.units.chessgame.gamelogic.move.core;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface MoveGenerator {
    
    List<ReversibleMove> generateMovesFrom(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation);
}