package com.sdm.units.chessgame.gui.pieces;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

@FunctionalInterface
public interface PieceViewFactory {

    JComponent createComponentFor(ChessPiece piece);
}