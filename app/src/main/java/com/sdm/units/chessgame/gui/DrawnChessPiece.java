package com.sdm.units.chessgame.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;

public class DrawnChessPiece extends JComponent {

    private ChessPieceDrawer chessPieceDrawer;
    private ChessPieceColor color;

    public DrawnChessPiece(ChessPieceDrawer chessPieceDrawer, ChessPieceColor color) {
        this.chessPieceDrawer = chessPieceDrawer;
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D scratch = (Graphics2D) g.create();
        try {
            Dimension size = getSize();
            scratch.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            chessPieceDrawer.drawChessPiece(scratch, size, color);
            this.setOpaque(true);
        } finally {
            scratch.dispose();
        }
    }
}