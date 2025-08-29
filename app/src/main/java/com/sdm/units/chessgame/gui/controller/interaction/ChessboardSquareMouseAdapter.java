package com.sdm.units.chessgame.gui.controller.interaction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;

public class ChessboardSquareMouseAdapter extends MouseAdapter {

    private final ChessboardSquareHandler squareHandler;
    private final SquareClickHandler clickHandler;
    private final SquareInteractionManager interactionManager;

    public ChessboardSquareMouseAdapter(ChessboardSquareHandler squareHandler, SquareClickHandler clickHandler, SquareInteractionManager interactionManager) {
        this.squareHandler = squareHandler;
        this.clickHandler = clickHandler;
        this.interactionManager = interactionManager;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        clickHandler.handleClick(squareHandler.getPosition());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        interactionManager.setHover(squareHandler);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        interactionManager.setSelectable(squareHandler, clickHandler);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        interactionManager.setClicked(squareHandler);
    }
}