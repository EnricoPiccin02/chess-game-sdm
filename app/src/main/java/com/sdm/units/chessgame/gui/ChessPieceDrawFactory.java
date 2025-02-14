package com.sdm.units.chessgame.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;

public class ChessPieceDrawFactory {
    
    public static void drawPawn(Graphics2D scratch, Dimension size, ChessPieceColor color){
        scratch.setColor(color.getEncodedColor());

        // Draw the pawn using basic shapes
        Dimension headSize = new Dimension(size.width / 3, size.height / 3);
        Dimension bodySize = new Dimension(size.width / 3, size.height / 2);
        Dimension baseSize = new Dimension(size.width / 4, size.height / 6);

        // Head (circle)
        scratch.fillOval(headSize.width, 0, headSize.width, headSize.height);

        // Body (rectangle)
        scratch.fillRect(bodySize.width, headSize.height, headSize.width, bodySize.height);

        // Base (rectangle)
        scratch.fillRect(baseSize.width, headSize.height + bodySize.height, bodySize.height, baseSize.height);
    }

    public static void drawRook(Graphics2D scratch, Dimension size, ChessPieceColor color){
        scratch.setColor(color.getEncodedColor());

        // Draw the rook using basic shapes
        Dimension headSize = new Dimension(size.width / 3, size.height / 3);
        Dimension bodySize = new Dimension(size.width / 3, size.height / 2);
        Dimension baseSize = new Dimension(size.width / 4, size.height / 6);

        // Head (circle)
        scratch.fillOval(headSize.width, 0, headSize.width, headSize.height);

        // Body (rectangle)
        scratch.fillRect(bodySize.width, headSize.height, headSize.width, bodySize.height);

        // Base (rectangle)
        scratch.fillRect(baseSize.width, headSize.height + bodySize.height, bodySize.height, baseSize.height);
    }

    public static void drawKnight(Graphics2D scratch, Dimension size, ChessPieceColor color){
        scratch.setColor(color.getEncodedColor());

        // Draw the knight using basic shapes
        Dimension headSize = new Dimension(size.width / 3, size.height / 3);
        Dimension bodySize = new Dimension(size.width / 3, size.height / 2);
        Dimension baseSize = new Dimension(size.width / 4, size.height / 6);

        // Head (circle)
        scratch.fillOval(headSize.width, 0, headSize.width, headSize.height);

        // Body (rectangle)
        scratch.fillRect(bodySize.width, headSize.height, headSize.width, bodySize.height);

        // Base (rectangle)
        scratch.fillRect(baseSize.width, headSize.height + bodySize.height, bodySize.height, baseSize.height);
    }

    public static void drawBishop(Graphics2D scratch, Dimension size, ChessPieceColor color){
        scratch.setColor(color.getEncodedColor());

        // Draw the bishop using basic shapes
        Dimension headSize = new Dimension(size.width / 3, size.height / 3);
        Dimension bodySize = new Dimension(size.width / 3, size.height / 2);
        Dimension baseSize = new Dimension(size.width / 4, size.height / 6);

        // Head (circle)
        scratch.fillOval(headSize.width, 0, headSize.width, headSize.height);

        // Body (rectangle)
        scratch.fillRect(bodySize.width, headSize.height, headSize.width, bodySize.height);

        // Base (rectangle)
        scratch.fillRect(baseSize.width, headSize.height + bodySize.height, bodySize.height, baseSize.height);
    }

    public static void drawQueen(Graphics2D scratch, Dimension size, ChessPieceColor color){
        scratch.setColor(color.getEncodedColor());

        // Draw the queen using basic shapes
        Dimension headSize = new Dimension(size.width / 3, size.height / 3);
        Dimension bodySize = new Dimension(size.width / 3, size.height / 2);
        Dimension baseSize = new Dimension(size.width / 4, size.height / 6);

        // Head (circle)
        scratch.fillOval(headSize.width, 0, headSize.width, headSize.height);

        // Body (rectangle)
        scratch.fillRect(bodySize.width, headSize.height, headSize.width, bodySize.height);

        // Base (rectangle)
        scratch.fillRect(baseSize.width, headSize.height + bodySize.height, bodySize.height, baseSize.height);
    }

    public static void drawKing(Graphics2D scratch, Dimension size, ChessPieceColor color){
        scratch.setColor(color.getEncodedColor());

        // Draw the king using basic shapes
        Dimension headSize = new Dimension(size.width / 3, size.height / 3);
        Dimension bodySize = new Dimension(size.width / 3, size.height / 2);
        Dimension baseSize = new Dimension(size.width / 4, size.height / 6);

        // Head (circle)
        scratch.fillOval(headSize.width, 0, headSize.width, headSize.height);

        // Body (rectangle)
        scratch.fillRect(bodySize.width, headSize.height, headSize.width, bodySize.height);

        // Base (rectangle)
        scratch.fillRect(baseSize.width, headSize.height + bodySize.height, bodySize.height, baseSize.height);
    }
}