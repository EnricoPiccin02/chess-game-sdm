package com.sdm.units.chessgame.gui.controller.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;

public class ChessboardInteractionController implements ChessboardInteractionStrategy {

    private final ChessboardView chessboardView;
    private final SquareInteractionManager interactionManager;
    private final List<SquareClickHandler> handlers = new ArrayList<>();

    public ChessboardInteractionController(ChessboardView chessboardView, SquareInteractionManager interactionManager) {
        this.chessboardView = chessboardView;
        this.interactionManager = interactionManager;
    }

    public void addClickHandler(SquareClickHandler handler) {
        handlers.add(handler);
    }

    private void fireClick(ChessboardPosition pos) {
        handlers.forEach(h -> h.handleClick(pos));
    }

    @Override
    public void enableSelectableSquares(Set<ChessboardPosition> positions) {
        chessboardView.updateSquaresAt(positions,
            square -> interactionManager.setSelectable(square, this::fireClick));
    }

    @Override
    public void clear() {
        chessboardView.updateAllSquares(square -> interactionManager.setNone(square));
    }
}