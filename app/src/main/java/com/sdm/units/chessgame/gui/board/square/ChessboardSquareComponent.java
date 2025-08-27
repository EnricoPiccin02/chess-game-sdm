package com.sdm.units.chessgame.gui.board.square;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardSquareMouseAdapter;
import com.sdm.units.chessgame.gui.pieces.PieceViewFactory;

public class ChessboardSquareComponent extends JPanel implements ChessboardSquareHandler {

    private final ChessboardPosition position;
    private final PieceViewFactory viewFactory;
    private final HighlightRenderer highlightRenderer;
    private JComponent pieceComponent;
    private MouseListener clickListener;

    public ChessboardSquareComponent(ChessboardPosition position, PieceViewFactory viewFactory, HighlightRenderer highlightRenderer) {
        super(new BorderLayout());
        this.position = position;
        this.viewFactory = viewFactory;
        this.highlightRenderer = highlightRenderer;

        setBackground(SquareColor.fromPosition(position).getColor());
    }

    @Override
    public ChessboardPosition getPosition() {
        return position;
    }

    @Override
    public void setPiece(Optional<ChessPiece> newPiece) {
        removeAll();
        newPiece.map(viewFactory::createComponentFor).ifPresent(c -> {
            pieceComponent = c;
            add(pieceComponent, BorderLayout.CENTER);
        });
        refresh();
    }

    public JComponent getPiece() {
        return pieceComponent;
    }

    @Override
    public void highlight(HighlightColor color) {
        highlightRenderer.apply(this, color);
    }

    @Override
    public void clearHighlight() {
        highlightRenderer.apply(this, HighlightColor.NONE);
    }

    @Override
    public void attachClickHandler(SquareClickHandler handler) {
        removeClickHandler();
        clickListener = new ChessboardSquareMouseAdapter(handler);
        addMouseListener(clickListener);
    }

    @Override
    public void removeClickHandler() {
        if (clickListener != null) {
            removeMouseListener(clickListener);
            clickListener = null;
        }
    }

    public MouseListener getClickListener() {
        return clickListener;
    }

    @Override
    public void refresh() {
        revalidate();
        repaint();
    }
}