package com.sdm.units.chessgame.gamelogic.move.core;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;

public abstract class CompositeMove implements ReversibleMove {
    
    private final List<ReversibleMove> moves;

    protected CompositeMove(ReversibleMove... moves) {
        this.moves = List.of(moves);
    }

    @Override
    public CaptureResult executeOn(Chessboard board) {
        CaptureResult lastCapture = CaptureResult.none();
        for (ReversibleMove move : moves) {
            CaptureResult result = move.executeOn(board);
            if (result.isCapture()) {
                lastCapture = result;
            }
        }
        return lastCapture;
    }

    @Override
    public CaptureResult undoOn(Chessboard board) {
        CaptureResult lastCapture = CaptureResult.none();
        for (int i = moves.size() - 1; i >= 0; i--) {
            CaptureResult result = moves.get(i).undoOn(board);
            if (result.isCapture()) {
                lastCapture = result;
            }
        }
        return lastCapture;
    }

    @Override
    public String toString() {
        return moves.stream()
            .map(Object::toString)
            .reduce((a, b) -> a + " + " + b)
            .orElse("");
    }
}