package com.sdm.units.chessgame.gui.controller.interaction;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public interface ChessboardInteractionStrategy {
    
    void enableSelectablePieces(Set<ChessboardPosition> selectablePositions);
    
    void enableLegalDestinations(Set<ChessboardPosition> destinations);
    
    void clear();
}