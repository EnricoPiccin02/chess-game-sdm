package com.sdm.units.chessgame.gamecontrol.state;

import com.sdm.units.chessgame.gamecontrol.flow.GameFlowController;
import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamelogic.board.evaluation.GameOutcomeEvaluator;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.state.LegalMoveFinder;
import com.sdm.units.chessgame.gamelogic.board.state.MoveExecutor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

public final class GameStateHandler implements GameStateController {

    private final Chessboard board;
    private final MoveExecutor executor;
    private final LegalMoveFinder moveFinder;
    private final TurnManager turns;
    private final GameOutcomeEvaluator outcomeEvaluator;
    private final GameFlowController flowController; 

    public GameStateHandler(Chessboard board, MoveExecutor executor, LegalMoveFinder moveFinder, TurnManager turns, GameOutcomeEvaluator outcomeEvaluator, GameFlowController flowController) {
        this.board = board;
        this.executor = executor;
        this.moveFinder = moveFinder;
        this.turns = turns;
        this.outcomeEvaluator = outcomeEvaluator;
        this.flowController = flowController;
    }

    @Override
    public void start() {
        board.resetBoard();
        executor.reset();
        flowController.onGameStart();
    }

    @Override
    public void makeMove(ChessboardPosition from, ChessboardPosition to) {
        moveFinder.createIfValid(board, from, to)
            .ifPresentOrElse(
                move -> applyMove(board, move),
                () -> flowController.onMoveRejected(GameReason.ILLEGAL_MOVE)
            );
    }

    public void applyMove(Chessboard board, ReversibleMove move) {
        ChessPieceColor mover = turns.current();
        ChessPieceColor opponent = turns.opponent();

        MoveResult result = executor.executeMove(board, move);

        if (outcomeEvaluator.isIllegalBecauseOfCheck(board, mover)) {
            executor.undoLastMove(board);
            flowController.onMoveRejected(GameReason.UNDER_ATTACK);
            return;
        }

        if (outcomeEvaluator.isCheckmate(board, opponent)) {
            proclaimWinner(mover, GameReason.CHECKMATE);
            return;
        }

        flowController.onMoveApplied(result);
    }

    @Override
    public void undoMove() {
        executor.undoLastMove(board)
            .ifPresentOrElse(
                flowController::onMoveUndone,
                () -> flowController.onMoveRejected(GameReason.NO_UNDO)
            );
    }

    @Override
    public void proclaimWinner(ChessPieceColor winner, GameReason reason) {
        flowController.onPlayerWon(winner, reason);
        start();
    }

    @Override
    public void end() {
        flowController.onGameEnd(GameReason.GAME_ENDED);
    }
}