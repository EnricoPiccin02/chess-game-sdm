package com.sdm.units.chessgame.gamelogic.movement;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface MovementStrategy {
    
    Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor);
}