package com.sdm.units.chessgame.gamelogic.basics;

import java.util.List;

public interface Movable {
    
    public List<ChessPieceMove> getPossibleMoves(ChessboardPosition fromPosition);

    public List<ChessPieceMove> getCaptureMoves(ChessboardPosition fromPosition);
}