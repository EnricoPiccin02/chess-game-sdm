package com.sdm.units.chessgame.gamelogic.move.core;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class CompositeMoveValidator implements MoveValidator {
    
    private final List<PrioritizedMoveValidator> rules;

    public CompositeMoveValidator(List<? extends PrioritizedMoveValidator> rules) {
        this.rules = rules.stream()
            .sorted(Comparator.comparing(PrioritizedMoveValidator::getPriority))
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<ReversibleMove> validateAndCreate(Chessboard board, ChessboardPosition from, ChessboardPosition to, ChessboardOrientation orientation) {
        for (MoveValidator rule : rules) {
            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from, to, orientation);
            if (maybeMove.isPresent()) return maybeMove;
        }
        return Optional.empty();
    }

    public List<PrioritizedMoveValidator> rules() {
        return rules;
    }
}