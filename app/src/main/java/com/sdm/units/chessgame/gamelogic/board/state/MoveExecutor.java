package com.sdm.units.chessgame.gamelogic.board.state;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

public interface MoveExecutor {

    MoveResult executeMove(Chessboard board, ReversibleMove move);

    Optional<MoveResult> undoLastMove(Chessboard board);

    void reset();
}