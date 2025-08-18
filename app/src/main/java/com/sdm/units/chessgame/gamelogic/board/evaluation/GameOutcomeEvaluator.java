package com.sdm.units.chessgame.gamelogic.board.evaluation;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public final class GameOutcomeEvaluator {

    private final AttackDetector attackDetector;
    private final CheckmateEvaluator checkmate;

    public GameOutcomeEvaluator(AttackDetector attackDetector, CheckmateEvaluator checkmate) {
        this.attackDetector = attackDetector;
        this.checkmate = checkmate;
    }

    public boolean isIllegalBecauseOfCheck(Chessboard board, ChessPieceColor mover) {
        return attackDetector.isUnderAttack(board, mover);
    }

    public boolean isCheckmate(Chessboard board, ChessPieceColor player) {
        return checkmate.isCheckmate(board, player);
    }
}