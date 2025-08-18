package com.sdm.units.chessgame.gamelogic.board.evaluation;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface PathSafetySimulator {
    
    boolean isPathSafe(Chessboard board, ChessboardPosition from, List<ChessboardPosition> path, ChessPieceColor color);
}