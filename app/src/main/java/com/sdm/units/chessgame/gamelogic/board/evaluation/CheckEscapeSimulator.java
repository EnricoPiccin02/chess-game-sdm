package com.sdm.units.chessgame.gamelogic.board.evaluation;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface CheckEscapeSimulator {
    
    boolean resolvesCheck(Chessboard board, ChessboardPosition from, ChessboardPosition to, ChessPieceColor defenderColor);
}