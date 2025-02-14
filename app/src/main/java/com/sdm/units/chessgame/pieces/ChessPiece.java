package com.sdm.units.chessgame.pieces;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.basics.Movable;
import com.sdm.units.chessgame.gui.DrawnChessPiece;

public abstract class ChessPiece implements Movable {
    
    protected boolean hasMoved;
    protected ChessPieceInfo info;
    public final ChessPieceColor color;
    
    ChessPiece(ChessPieceColor color) {
        this.color = color;
        this.hasMoved = false;
    }

    public abstract DrawnChessPiece drawChessPiece();

    public void setHasMoved() {
        this.hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public ChessPieceInfo pieceInfo() {
        return info;
    }

    public ChessPieceColor pieceColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ChessPiece other = (ChessPiece) obj;

        return color == other.color && info == other.info;
    }
}