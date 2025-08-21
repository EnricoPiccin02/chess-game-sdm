package com.sdm.units.chessgame.gamelogic.board.evaluation;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

@FunctionalInterface
public interface AttackDetector {
    
    boolean isUnderAttack(Chessboard board, ChessPieceColor defenderColor);    
}