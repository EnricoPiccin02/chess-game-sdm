package com.sdm.units.chessgame.gamelogic.move.core;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public abstract class SingleMove implements ReversibleMove {

    private final MoveComponent component;

    protected SingleMove(ChessboardPosition from, ChessboardPosition to) {
        this.component = new MoveComponent(from, to);
    }

    @Override
    public MoveComponent getPrimaryMoveComponent() {
        return component;
    }

    public ChessboardPosition from() {
        return component.from();
    }

    public ChessboardPosition to() {
        return component.to();
    }

    @Override
    public String toString() {
        return component.toString();
    }
}