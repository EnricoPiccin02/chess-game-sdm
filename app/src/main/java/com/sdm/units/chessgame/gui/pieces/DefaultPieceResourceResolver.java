package com.sdm.units.chessgame.gui.pieces;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class DefaultPieceResourceResolver implements PieceResourceResolver {
    
    @Override
    public String resolvePath(ChessPiece piece) {
        String color = piece.pieceColor().name().toLowerCase();
        String type = piece.getClass().getSimpleName().toLowerCase();
        
        return "images/pieces/" + color + "/" + type + ".svg";
    }
}