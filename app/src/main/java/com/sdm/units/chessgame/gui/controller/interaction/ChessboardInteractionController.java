package com.sdm.units.chessgame.gui.controller.interaction;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gui.board.square.HighlightStyle;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;

public class ChessboardInteractionController implements ChessboardInteractionStrategy {

    private final ChessboardView chessboardView;
    private SquareClickHandler clickHandler;

    public ChessboardInteractionController(ChessboardView chessboardView) {
        this.chessboardView = chessboardView;
    }

    public void setClickHandler(SquareClickHandler handler) {
        this.clickHandler = handler;
    }

    @Override
    public void enableSelectablePieces(Set<ChessboardPosition> positions) {
        chessboardView.updateSquaresAt(positions,
            square -> HighlightStyle.SELECTABLE.apply(square, clickHandler));
    }

    @Override
    public void enableLegalDestinations(Set<ChessboardPosition> positions) {
        chessboardView.updateSquaresAt(positions,
            square -> HighlightStyle.LEGAL_DESTINATION.apply(square, clickHandler));
    }

    @Override
    public void clear() {
        chessboardView.updateAllSquares(
            square -> HighlightStyle.NONE.apply(square, clickHandler));
    }
}