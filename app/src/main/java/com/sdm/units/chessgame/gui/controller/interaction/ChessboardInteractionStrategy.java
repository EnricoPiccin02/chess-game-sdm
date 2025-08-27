package com.sdm.units.chessgame.gui.controller.interaction;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public interface ChessboardInteractionStrategy {
    
    void enableSelectableSquares(Set<ChessboardPosition> selectablePositions);
    
    void clear();
}