package com.sdm.units.chessgame.gamelogic.move.core;

@FunctionalInterface
public interface PrimaryMoveComponentProvider {
   
    MoveComponent getPrimaryMoveComponent();
}