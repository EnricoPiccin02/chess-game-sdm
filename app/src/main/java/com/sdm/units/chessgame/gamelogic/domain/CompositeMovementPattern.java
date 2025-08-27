package com.sdm.units.chessgame.gamelogic.domain;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.*;

import java.util.List;

public enum CompositeMovementPattern {

    L_SHAPED(List.of(
        List.of(LEFT, LEFT, DOWN),
        List.of(LEFT, LEFT, UP),
        List.of(UP, UP, LEFT),
        List.of(UP, UP, RIGHT),
        List.of(RIGHT, RIGHT, UP),
        List.of(RIGHT, RIGHT, DOWN),
        List.of(DOWN, DOWN, RIGHT),
        List.of(DOWN, DOWN, LEFT)
    ));

    private final List<List<ChessboardDirection>> compositeDirections;

    CompositeMovementPattern(List<List<ChessboardDirection>> compositeDirections) {
        this.compositeDirections = List.copyOf(compositeDirections);
    }

    public List<List<ChessboardDirection>> getCompositeDirections() {
        return compositeDirections;
    }
}