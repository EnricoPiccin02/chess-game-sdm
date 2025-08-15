package com.sdm.units.chessgame.gamelogic.board;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.move.core.ChessMove;

public interface MoveRecorder<T extends ChessMove> {

    void pushMove(T move);

    Optional<T> popMove();

    Optional<T> getLastMove();
}