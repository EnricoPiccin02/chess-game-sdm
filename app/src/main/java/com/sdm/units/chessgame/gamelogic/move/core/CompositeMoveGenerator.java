package com.sdm.units.chessgame.gamelogic.move.core;

import java.util.List;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class CompositeMoveGenerator implements MoveGenerator {

    private final List<MoveGenerator> rules;

    public CompositeMoveGenerator(List<? extends MoveGenerator> rules) {
        this.rules = rules.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ReversibleMove> generateMovesFrom(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
        return rules.stream()
            .flatMap(rule -> rule.generateMovesFrom(board, from, orientation).stream())
            .collect(Collectors.toList());
    }
}