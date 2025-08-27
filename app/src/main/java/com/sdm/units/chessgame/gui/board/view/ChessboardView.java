package com.sdm.units.chessgame.gui.board.view;

import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;

public interface ChessboardView {

    void renderChessboardState(Chessboard board);

    void updateSquaresAt(Set<ChessboardPosition> positions, Consumer<? super ChessboardSquareHandler> operation);

    void updateAllSquares(Consumer<? super ChessboardSquareHandler> operation);

    JComponent asComponent();
}