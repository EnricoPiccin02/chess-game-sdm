package com.sdm.units.chessgame.gamelogic.board.evaluation;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

@FunctionalInterface
public interface CheckmateEvaluator {
    
    boolean isCheckmate(Chessboard board, ChessPieceColor defenderColor);
}