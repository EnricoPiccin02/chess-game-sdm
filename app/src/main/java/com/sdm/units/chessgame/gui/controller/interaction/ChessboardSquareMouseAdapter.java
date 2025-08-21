package com.sdm.units.chessgame.gui.controller.interaction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.sdm.units.chessgame.gui.board.square.ChessboardSquareComponent;
import com.sdm.units.chessgame.gui.board.square.HighlightStyle;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;

public class ChessboardSquareMouseAdapter extends MouseAdapter {

    private final SquareClickHandler clickHandler;

    public ChessboardSquareMouseAdapter(SquareClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (!(event.getSource() instanceof ChessboardSquareComponent square)) return;
        clickHandler.handleClick(square.getPosition());
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        if (event.getSource() instanceof ChessboardSquareComponent square) {
            HighlightStyle.HOVER.apply(square, clickHandler);
        }
    }

    @Override
    public void mouseExited(MouseEvent event) {
        if (event.getSource() instanceof ChessboardSquareComponent square) {
            HighlightStyle.SELECTABLE.apply(square, clickHandler);
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getSource() instanceof ChessboardSquareComponent square) {
            HighlightStyle.CLICKED.apply(square, clickHandler);
        }
    }
}