package com.sdm.units.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.ChessPieceMovement;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gui.DrawnChessPiece;

public abstract class ChessPiece implements ChessPieceMovement {
    
    protected ChessboardPosition position;
    protected ChessPieceColor color;

    ChessPiece(ChessboardPosition position, ChessPieceColor color) {
        this.position = position;
        this.color = color;
    }

    public abstract List<ChessboardPosition> getStartingPositions();

    public abstract DrawnChessPiece drawChessPiece();
}