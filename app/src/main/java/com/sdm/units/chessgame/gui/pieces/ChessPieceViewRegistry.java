package com.sdm.units.chessgame.gui.pieces;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class ChessPieceViewRegistry {
    
    private final SvgRenderer renderer;
    private final PieceResourceResolver resolver;

    public ChessPieceViewRegistry(SvgRenderer renderer, PieceResourceResolver resolver) {
        this.renderer = renderer;
        this.resolver = resolver;
    }

    public JComponent createComponentFor(ChessPiece piece) {
        String path = resolver.resolvePath(piece);
        return new SvgImagePanel(path, renderer);
    }
}