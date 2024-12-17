package com.sdm.units.chessgame.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;

@FunctionalInterface
public interface ChessPieceDrawer {

    public void drawChessPiece(Graphics2D scratch, Dimension size, ChessPieceColor color);
}