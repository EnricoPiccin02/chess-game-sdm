package com.sdm.units.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gui.ChessPieceDrawFactory;
import com.sdm.units.chessgame.gui.DrawnChessPiece;

public class Pawn extends ChessPiece {

    public Pawn(ChessboardPosition position, ChessPieceColor color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(ChessboardPosition newPosition) {
        return true;
    }

    @Override
    public List<ChessboardPosition> getValidMoves() {
        return null;
    }

    @Override
    public List<ChessboardPosition> getStartingPositions() {
        return null;
    }

    @Override
    public DrawnChessPiece drawChessPiece() {
        return new DrawnChessPiece(ChessPieceDrawFactory::drawPawn, color);
    }
}