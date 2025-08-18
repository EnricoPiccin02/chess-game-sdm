package com.sdm.units.chessgame.gamelogic.board.state;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.MoveGenerator;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public class LegalMoveFinder {

    private final MoveValidator validator;
    private final MoveGenerator generator;
    private final ChessboardOrientation orientation;

    public LegalMoveFinder(MoveValidator validator, MoveGenerator generator, ChessboardOrientation orientation) {
        this.validator = validator;
        this.generator = generator;
        this.orientation = orientation;
    }

    public Optional<ReversibleMove> createIfValid(Chessboard board, ChessboardPosition from, ChessboardPosition to) {
        return validator.validateAndCreate(board, from, to, orientation);
    }

    public Set<ChessboardPosition> findLegalDestinations(Chessboard board, ChessboardPosition from) {
        return generator.generateMovesFrom(board, from, orientation).stream()
            .map(ReversibleMove::getPrimaryMoveComponent)
            .map(MoveComponent::to)
            .collect(Collectors.toSet());
    }
}