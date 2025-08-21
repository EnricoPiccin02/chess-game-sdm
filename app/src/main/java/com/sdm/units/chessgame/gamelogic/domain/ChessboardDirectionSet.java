package com.sdm.units.chessgame.gamelogic.domain;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.*;

import java.util.Arrays;
import java.util.List;

public enum ChessboardDirectionSet {

    ORTHOGONAL(List.of(UP, DOWN, LEFT, RIGHT)),
    DIAGONAL(List.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)),
    ORTHOGONAL_AND_DIAGONAL(combine(ORTHOGONAL, DIAGONAL));

    private final List<ChessboardDirection> directions;

    ChessboardDirectionSet(List<ChessboardDirection> directions) {
        this.directions = directions;
    }

    public List<ChessboardDirection> getDirections() {
        return this.directions;
    }

    private static List<ChessboardDirection> combine(ChessboardDirectionSet... sets) {
        return Arrays.stream(sets)
            .flatMap(set -> set.directions.stream())
            .toList();
    }
}