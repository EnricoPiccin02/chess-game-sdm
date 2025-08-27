package com.sdm.units.chessgame.gamelogic.movement;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;

@FunctionalInterface
public interface DirectionProvider {
    
    Set<ChessboardDirection> getDirections();
}