package com.sdm.units.chessgame.gamelogic.move.special;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public interface SpecialMovePattern<SpecialMoveCandidate> {

    List<SpecialMoveCandidate> findCandidates(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation);
    
    Optional<SpecialMoveCandidate> buildCandidate(Chessboard board, ChessboardPosition from, ChessboardPosition to);
}