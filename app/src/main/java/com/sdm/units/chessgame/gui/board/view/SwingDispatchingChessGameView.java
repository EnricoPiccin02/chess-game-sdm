package com.sdm.units.chessgame.gui.board.view;

import javax.swing.SwingUtilities;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public class SwingDispatchingChessGameView implements ChessGameView {

    private final ChessGameView view;

    public SwingDispatchingChessGameView(ChessGameView view) {
        this.view = view;
    }

    private void invoke(Runnable task) {
        if (SwingUtilities.isEventDispatchThread()) task.run();
        else SwingUtilities.invokeLater(task);
    }

    @Override
    public void display() {
        invoke(view::display);
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