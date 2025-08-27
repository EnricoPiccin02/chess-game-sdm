package com.sdm.units.chessgame.gui.controller.interaction;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface InteractionState {
    
    void onSquareClicked(ChessboardPosition clickedPosition);

    default void onEnter() {}
}