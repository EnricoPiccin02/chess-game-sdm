package com.sdm.units.chessgame.gamelogic.board.evaluation;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public interface GameOutcomeEvaluator {

    boolean isIllegalBecauseOfCheck(Chessboard board, ChessPieceColor mover);

    boolean isCheckmate(Chessboard board, ChessPieceColor player);
}