package com.sdm.units.chessgame.gamelogic;

import java.util.List;

public interface ChessPieceMovement {
    
    public boolean isValidMove(ChessboardPosition newPosition);
    
    public List<ChessboardPosition> getValidMoves();
}