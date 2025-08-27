package com.sdm.units.chessgame.gui.board.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareComponent;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;
import com.sdm.units.chessgame.gui.board.square.SquareSize;
import com.sdm.units.chessgame.gui.pieces.PieceViewFactory;

public class ChessboardPanel extends JPanel implements ChessboardView {

    private final PieceViewFactory viewFactory;
    private final HighlightRenderer highlightRenderer;
    private final Map<ChessboardPosition, ChessboardSquareHandler> squareComponents = new HashMap<>();

    public ChessboardPanel(PieceViewFactory viewFactory, HighlightRenderer highlightRenderer) {
        this.viewFactory = viewFactory;
        this.highlightRenderer = highlightRenderer;
        setLayout(new GridLayout(ChessboardRank.values().length, ChessboardFile.values().length));

        initializeSquares();
    }

    private void initializeSquares() {
        Arrays.stream(ChessboardRank.values())
            .sorted(Comparator.reverseOrder())
            .forEach(rank ->
                Arrays.stream(ChessboardFile.values())
                    .forEach(file -> {
                        ChessboardPosition position = new ChessboardPosition(file, rank);
                        ChessboardSquareComponent square = new ChessboardSquareComponent(position, viewFactory, highlightRenderer);
                        square.setPreferredSize(new Dimension(
                            SquareSize.SQUARE_WIDTH.getSize(),
                            SquareSize.SQUARE_HEIGHT.getSize()
                        ));
                        add(square);
                        squareComponents.put(position, square);
                    })
            );
    }

    public ChessboardSquareHandler getSquareAt(ChessboardPosition position) {
        return squareComponents.get(position);
    }

    public Collection<ChessboardSquareHandler> getAllSquares() {
        return squareComponents.values();
    }

    public void replaceSquare(ChessboardPosition position, ChessboardSquareHandler newSquare) {
        squareComponents.put(position, newSquare);
    }

    @Override
    public void renderChessboardState(Chessboard board) {
        squareComponents.forEach((position, square) -> square.setPiece(board.getPieceAt(position)));
        repaint();
    }

    @Override
    public void updateSquaresAt(Set<ChessboardPosition> positions, Consumer<? super ChessboardSquareHandler> operation) {
        positions.stream()
            .map(squareComponents::get)
            .filter(Predicate.not(Objects::isNull))
            .forEach(operation::accept);
    }

    @Override
    public void updateAllSquares(Consumer<? super ChessboardSquareHandler> operation) {
        squareComponents.entrySet()
            .stream()
            .map(Entry::getValue)
            .forEach(operation::accept);
    }

    @Override
    public JComponent asComponent() {
        return this;
    }
}