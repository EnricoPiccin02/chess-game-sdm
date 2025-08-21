package com.sdm.units.chessgame.gamelogic.move.core;

public interface PrioritizedMoveValidator extends MoveValidator {
    
    int getPriority(); // Lower value = higher priority
}