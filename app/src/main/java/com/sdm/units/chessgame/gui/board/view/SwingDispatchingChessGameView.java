package com.sdm.units.chessgame.gui.board.view;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;

public class SwingDispatchingChessGameView implements ChessGameView {

    private final ChessGameView view;
    private final InteractionContext interactionContext;
    private final SwingDispatcher dispatcher;

    public SwingDispatchingChessGameView(ChessGameView view, InteractionContext interactionContext, SwingDispatcher dispatcher) {
        this.view = view;
        this.interactionContext = interactionContext;
        this.dispatcher = dispatcher;
    }

    private void invoke(Runnable task) {
        dispatcher.dispatch(task);
    }

    @Override
    public void initialize() {
        invoke(view::initialize);
        interactionContext.initialize();
    }

    @Override
    public void setChessboard(Chessboard board) {
        invoke(() -> view.setChessboard(board));
    }

    @Override
    public void displayMessage(String messageToDisplay) {
        invoke(() -> view.displayMessage(messageToDisplay));
    }

    @Override
    public void recordMessage(String messageToRecord) {
        invoke(() -> view.recordMessage(messageToRecord));
    }

    @Override
    public void startClock(ChessPieceColor color) {
        invoke(() -> view.startClock(color));
    }

    @Override
    public void stopClock(ChessPieceColor color) {
        invoke(() -> view.stopClock(color));
    }

    @Override
    public void close() {
        invoke(view::close);
    }

    @Override
    public void reset() {
        invoke(view::reset);
    }
}