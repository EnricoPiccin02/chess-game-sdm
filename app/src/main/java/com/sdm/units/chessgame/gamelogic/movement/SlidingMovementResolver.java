package com.sdm.units.chessgame.gamelogic.movement;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface SlidingMovementResolver {
    
    Set<ChessboardPosition> getSlidingDestinations(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor, Set<ChessboardDirection> directions);
}