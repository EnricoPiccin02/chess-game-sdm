package com.sdm.units.chessgame.gamelogic.pieces;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public interface ChessPiece {
    
    void markAsMoved();

    boolean hasMoved();

    ChessPieceInfo pieceInfo();

    ChessPieceColor pieceColor();

    boolean isOpponentOf(ChessPieceColor color);

    Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition from);

    ChessPieceSnapshot createSnapshot();

    void restoreSnapshot(ChessPieceSnapshot snapshot);
}