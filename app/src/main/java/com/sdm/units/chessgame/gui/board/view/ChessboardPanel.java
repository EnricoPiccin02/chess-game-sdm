package com.sdm.units.chessgame.gui.board.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareComponent;
import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;
import com.sdm.units.chessgame.gui.board.square.SquareSize;
import com.sdm.units.chessgame.gui.pieces.ChessPieceViewRegistry;

public class ChessboardPanel extends JPanel implements ChessboardView {

    private final ChessPieceViewRegistry viewRegistry;
    private final HighlightRenderer highlightRenderer;
    private final Map<ChessboardPosition, ChessboardSquareComponent> squareComponents = new HashMap<>();

    public ChessboardPanel(ChessPieceViewRegistry viewRegistry, HighlightRenderer highlightRenderer) {
        this.viewRegistry = viewRegistry;
        this.highlightRenderer = highlightRenderer;
        setLayout(new GridLayout(ChessboardRank.values().length, ChessboardFile.values().length));
        setPreferredSize(new Dimension(
            ChessboardFile.values().length * SquareSize.SQUARE_WIDTH.getSize(),
            ChessboardRank.values().length * SquareSize.SQUARE_HEIGHT.getSize()
        ));

        initializeSquares();
    }

    private void initializeSquares() {
        for (int rank = ChessboardRank.values().length - 1; rank >= 0; rank--) {
            for (int file = 0; file < ChessboardFile.values().length; file++) {
                ChessboardFile f = ChessboardFile.values()[file];
                ChessboardRank r = ChessboardRank.values()[rank];
                ChessboardPosition position = new ChessboardPosition(f, r);

                ChessboardSquareComponent square = new ChessboardSquareComponent(position, viewRegistry, highlightRenderer);
                square.setPreferredSize(new Dimension(SquareSize.SQUARE_WIDTH.getSize(), SquareSize.SQUARE_HEIGHT.getSize()));
                add(square);
                squareComponents.put(position, square);
            }
        }
    }

    public ChessboardSquareComponent getSquareAt(ChessboardPosition position) {
        return squareComponents.get(position);
    }

    public Collection<ChessboardSquareComponent> getAllSquares() {
        return squareComponents.values();
    }

    public void replaceSquare(ChessboardPosition position, ChessboardSquareComponent newSquare) {
        squareComponents.put(position, newSquare);
    }

    @Override
    public void renderChessboardState(Chessboard board) {
        squareComponents.forEach((position, square) -> {
            Optional<ChessPiece> piece = board.getPieceAt(position);
            square.setPiece(piece);
        });
        repaint();
    }

    @Override
    public void updateSquaresAt(Set<ChessboardPosition> positions, Consumer<? super ChessboardSquareComponent> operation) {
        positions.stream()
            .map(squareComponents::get)
            .filter(Predicate.not(Objects::isNull))
            .forEach(operation::accept);
    }

    @Override
    public void updateAllSquares(Consumer<? super ChessboardSquareComponent> operation) {
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