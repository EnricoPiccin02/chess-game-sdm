package com.sdm.units.chessgame.gui.board.square;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardSquareMouseAdapter;
import com.sdm.units.chessgame.gui.pieces.ChessPieceViewRegistry;

public class ChessboardSquareComponent extends JPanel implements SquareRenderer {

    private final ChessboardPosition position;
    private final ChessPieceViewRegistry viewRegistry;
    private final HighlightRenderer highlightRenderer;
    private JComponent pieceComponent;
    private MouseListener clickListener;

    public ChessboardSquareComponent(ChessboardPosition position, ChessPieceViewRegistry viewRegistry, HighlightRenderer highlightRenderer) {
        super(new BorderLayout());
        this.position = position;
        this.viewRegistry = viewRegistry;
        this.highlightRenderer = highlightRenderer;

        setBackground(SquareColor.fromPosition(position).getColor());
    }

    public ChessboardPosition getPosition() {
        return position;
    }

    @Override
    public void setPiece(Optional<ChessPiece> newPiece) {
        removeAll();
        newPiece.map(viewRegistry::createComponentFor).ifPresent(c -> {
            pieceComponent = c;
            add(pieceComponent, BorderLayout.CENTER);
        });
        refresh();
    }

    public JComponent getPiece() {
        return pieceComponent;
    }

    @Override
    public void highlight(HighlightColor type) {
        highlightRenderer.apply(this, Optional.ofNullable(type));
    }

    @Override
    public void clearHighlight() {
        highlightRenderer.apply(this, Optional.empty());
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