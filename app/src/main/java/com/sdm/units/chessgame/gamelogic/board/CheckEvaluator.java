package com.sdm.units.chessgame.gamelogic.board;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public interface CheckEvaluator {
    
    boolean isUnderAttack(Chessboard board, ChessPieceColor defenderColor);
    
    boolean isCheckmate(Chessboard board, ChessPieceColor defenderColor);
}
