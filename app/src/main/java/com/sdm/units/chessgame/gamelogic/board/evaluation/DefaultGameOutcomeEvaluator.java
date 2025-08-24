package com.sdm.units.chessgame.gamelogic.board.evaluation;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public class DefaultGameOutcomeEvaluator implements GameOutcomeEvaluator {

    private final AttackDetector attackDetector;
    private final CheckmateEvaluator checkmate;

    public DefaultGameOutcomeEvaluator(AttackDetector attackDetector, CheckmateEvaluator checkmate) {
        this.attackDetector = attackDetector;
        this.checkmate = checkmate;
    }

    @Override
    public boolean isIllegalBecauseOfCheck(Chessboard board, ChessPieceColor mover) {
        return attackDetector.isUnderAttack(board, mover);
    }

    @Override
    public boolean isCheckmate(Chessboard board, ChessPieceColor player) {
        return checkmate.isCheckmate(board, player);
    }
}