package com.sdm.units.chessgame.gamelogic.board.state;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public class MoveHistory implements MoveRecorder<ReversibleMove> {

    private final Deque<ReversibleMove> moves = new ArrayDeque<>();

    @Override
    public void pushMove(ReversibleMove move) {
        moves.push(move);
    }

    @Override
    public Optional<ReversibleMove> popMove() {
        return moves.isEmpty() ? Optional.empty() : Optional.of(moves.pop());
    }

    @Override
    public Optional<ReversibleMove> getLastMove() {
        return moves.isEmpty() ? Optional.empty() : Optional.of(moves.getFirst());
    }

    @Override
    public void clear() {
        moves.clear();
    }
}