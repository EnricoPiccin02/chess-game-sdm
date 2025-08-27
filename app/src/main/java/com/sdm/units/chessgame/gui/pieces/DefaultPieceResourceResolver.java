package com.sdm.units.chessgame.gui.pieces;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class DefaultPieceResourceResolver implements PieceResourceResolver {
    
    @Override
    public String resolvePath(ChessPiece piece) {
        String color = piece.pieceColor().toString().toLowerCase();
        String info = piece.pieceInfo().toString().toLowerCase();

        return "images/pieces/" + color + "/" + info + ".svg";
    }
}