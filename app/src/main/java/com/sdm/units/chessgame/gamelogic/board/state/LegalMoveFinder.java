package com.sdm.units.chessgame.gamelogic.board.state;

import java.util.Optional;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public interface LegalMoveFinder {

    Optional<ReversibleMove> createIfValid(Chessboard board, ChessboardPosition from, ChessboardPosition to);

    Set<ChessboardPosition> findLegalDestinations(Chessboard board, ChessboardPosition from);
}