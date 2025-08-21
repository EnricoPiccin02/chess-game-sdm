package com.sdm.units.chessgame.gamelogic.board.state;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

public class MoveExecutorService implements MoveExecutor {

    private final MoveRecorder<ReversibleMove> history;

    public MoveExecutorService(MoveRecorder<ReversibleMove> history) {
        this.history = history;
    }

    @Override
    public MoveResult executeMove(Chessboard board, ReversibleMove move) {
        CaptureResult capture = move.executeOn(board);
        history.pushMove(move);
        return MoveResult.of(move, capture);
    }

    @Override
    public Optional<MoveResult> undoLastMove(Chessboard board) {
        return history.popMove().map(move -> {
            CaptureResult capture = move.undoOn(board);
            return MoveResult.of(move, capture);
        });
    }

    @Override
    public void reset() {
        history.clear();
    }
}