package com.sdm.units.chessgame.gamelogic.move.core;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public record MoveComponent(ChessboardPosition from, ChessboardPosition to) {

    @Override
    public String toString() {
        return from + " → " + to;
    }
}