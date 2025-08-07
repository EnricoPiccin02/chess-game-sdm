package com.sdm.units.chessgame.gamelogic.movement;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;

@FunctionalInterface
public interface CompositeDirectionProvider {

    List<List<ChessboardDirection>> getCompositeDirections();
}