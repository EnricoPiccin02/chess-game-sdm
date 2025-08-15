package com.sdm.units.chessgame.gamelogic.move.special;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.ComposedMoveRule;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public abstract class SpecialMoveRule<SpecialMoveCandidate> implements ComposedMoveRule {

    private final SpecialMovePattern<SpecialMoveCandidate> pattern;
    private final SpecialMoveEligibility<SpecialMoveCandidate> eligibility;
    private final SpecialMoveFactory<SpecialMoveCandidate> factory;

    protected SpecialMoveRule(SpecialMovePattern<SpecialMoveCandidate> pattern, SpecialMoveEligibility<SpecialMoveCandidate> eligibility, SpecialMoveFactory<SpecialMoveCandidate> factory) {
        this.pattern = pattern;
        this.eligibility = eligibility;
        this.factory = factory;
    }

    @Override
    public List<ReversibleMove> generateMovesFrom(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
        return board.getPieceAt(from)
            .map(piece -> pattern.findCandidates(board, from, orientation))
            .stream()
            .flatMap(List::stream)
            .filter(candidate -> eligibility.canExecute(board, candidate, orientation))
            .map(factory::create)
            .toList();
    }

    @Override
    public Optional<ReversibleMove> validateAndCreate(Chessboard board, ChessboardPosition from, ChessboardPosition to, ChessboardOrientation orientation) {
        return pattern.buildCandidate(board, from, to)
            .filter(candidate -> eligibility.canExecute(board, candidate, orientation))
            .map(factory::create);
    }
}