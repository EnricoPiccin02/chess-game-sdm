package com.sdm.units.chessgame.gui.controller.interaction;

import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;

public interface SquareInteractionManager {
    
    void setNone(ChessboardSquareHandler square);

    void setSelectable(ChessboardSquareHandler square, SquareClickHandler handler);

    void setHover(ChessboardSquareHandler square);

    void setClicked(ChessboardSquareHandler square);
}