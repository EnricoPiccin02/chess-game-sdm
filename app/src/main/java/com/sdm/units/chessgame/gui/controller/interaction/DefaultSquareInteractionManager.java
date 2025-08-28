package com.sdm.units.chessgame.gui.controller.interaction;

import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;

public class DefaultSquareInteractionManager implements SquareInteractionManager {

    @Override
    public void setNone(ChessboardSquareHandler square) {
        square.clearHighlight();
        square.removeClickHandler();
    }

    @Override
    public void setSelectable(ChessboardSquareHandler square, SquareClickHandler handler) {
        square.highlight(HighlightColor.SELECTABLE);
        square.setClickHandler(handler, this);
    }

    @Override
    public void setHover(ChessboardSquareHandler square) {
        square.highlight(HighlightColor.HOVER);
    }

    @Override
    public void setClicked(ChessboardSquareHandler square) {
        square.highlight(HighlightColor.CLICKED);
    }
}