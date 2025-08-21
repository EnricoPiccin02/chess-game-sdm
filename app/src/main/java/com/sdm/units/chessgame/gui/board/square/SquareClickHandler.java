package com.sdm.units.chessgame.gui.board.square;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface SquareClickHandler {
    
    void handleClick(ChessboardPosition position);
}