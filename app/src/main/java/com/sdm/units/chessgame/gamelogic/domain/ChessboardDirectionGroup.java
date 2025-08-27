package com.sdm.units.chessgame.gamelogic.domain;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.DOWN;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.DOWN_LEFT;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.DOWN_RIGHT;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.LEFT;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.RIGHT;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.UP;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.UP_LEFT;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection.UP_RIGHT;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum ChessboardDirectionGroup {
    
    ORTHOGONAL(Set.of(UP, DOWN, LEFT, RIGHT)),
    DIAGONAL(Set.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)),
    ORTHOGONAL_AND_DIAGONAL(combine(ORTHOGONAL, DIAGONAL));

    private final Set<ChessboardDirection> directions;

    ChessboardDirectionGroup(Set<ChessboardDirection> directions) {
        this.directions = Set.copyOf(directions);
    }

    public Set<ChessboardDirection> getDirections() {
        return directions;
    }

    private static Set<ChessboardDirection> combine(ChessboardDirectionGroup... groups) {
        return Arrays.stream(groups)
            .flatMap(g -> g.directions.stream())
            .collect(Collectors.toUnmodifiableSet());
    }
}