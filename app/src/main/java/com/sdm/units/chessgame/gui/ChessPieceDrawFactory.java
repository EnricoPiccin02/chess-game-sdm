package com.sdm.units.chessgame.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;

public class ChessPieceDrawFactory {

    private static final int OFFSET = 5;

    private static void drawChessPieceFromSvg(String chessPieceSvgPath, Graphics2D scratch, Dimension size) {
        BufferedImage svgImage = SvgImageUtils.renderSvgToImage(chessPieceSvgPath, (float) size.getWidth() - OFFSET, (float) size.getHeight() - OFFSET);

        if (svgImage != null)
            scratch.drawImage(svgImage, OFFSET / 2, OFFSET / 2, null);
    }

    public static void drawPawn(Graphics2D scratch, Dimension size, ChessPieceColor color) {
        String pawnSvgPath = switch (color) {
            case ChessPieceColor.BLACK -> "images/pieces/black/pawn.svg";
            case ChessPieceColor.WHITE -> "images/pieces/white/pawn.svg";
        };

        drawChessPieceFromSvg(pawnSvgPath, scratch, size);
    }

    public static void drawRook(Graphics2D scratch, Dimension size, ChessPieceColor color){
        String rookSvgPath = switch (color) {
            case ChessPieceColor.BLACK -> "images/pieces/black/rook.svg";
            case ChessPieceColor.WHITE -> "images/pieces/white/rook.svg";
        };

        drawChessPieceFromSvg(rookSvgPath, scratch, size);
    }

    public static void drawKnight(Graphics2D scratch, Dimension size, ChessPieceColor color){
        String knightSvgPath = switch (color) {
            case ChessPieceColor.BLACK -> "images/pieces/black/knight.svg";
            case ChessPieceColor.WHITE -> "images/pieces/white/knight.svg";
        };

        drawChessPieceFromSvg(knightSvgPath, scratch, size);
    }

    public static void drawBishop(Graphics2D scratch, Dimension size, ChessPieceColor color){
        String bishopSvgPath = switch (color) {
            case ChessPieceColor.BLACK -> "images/pieces/black/bishop.svg";
            case ChessPieceColor.WHITE -> "images/pieces/white/bishop.svg";
        };

        drawChessPieceFromSvg(bishopSvgPath, scratch, size);
    }

    public static void drawQueen(Graphics2D scratch, Dimension size, ChessPieceColor color){
        String queenSvgPath = switch (color) {
            case ChessPieceColor.BLACK -> "images/pieces/black/queen.svg";
            case ChessPieceColor.WHITE -> "images/pieces/white/queen.svg";
        };

        drawChessPieceFromSvg(queenSvgPath, scratch, size);
    }

    public static void drawKing(Graphics2D scratch, Dimension size, ChessPieceColor color){
        String kingSvgPath = switch (color) {
            case ChessPieceColor.BLACK -> "images/pieces/black/king.svg";
            case ChessPieceColor.WHITE -> "images/pieces/white/king.svg";
        };

        drawChessPieceFromSvg(kingSvgPath, scratch, size);
    }
}