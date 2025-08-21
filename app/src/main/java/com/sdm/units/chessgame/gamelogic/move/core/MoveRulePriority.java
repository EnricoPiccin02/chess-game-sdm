package com.sdm.units.chessgame.gamelogic.move.core;

public enum MoveRulePriority {
       
    LOW_PRIORITY, MEDIUM_PRIORITY, HIGH_PRIORITY;

    public int getPriority() {
        return switch (this) {
            case LOW_PRIORITY -> 2;
            case MEDIUM_PRIORITY -> 1;
            case HIGH_PRIORITY -> 0;
        };
    }
}