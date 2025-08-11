package com.sdm.units.chessgame.gamelogic.move.api;

public interface PrioritizedMoveValidator extends MoveValidator {
    
    int getPriority(); // Lower value = higher priority
}