package com.sdm.units.chessgame.gamelogic.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public interface ChessPiece extends CloneableChessPiece {
    
    void markAsMoved();

    boolean hasMoved();

    ChessPieceInfo pieceInfo();

    ChessPieceColor pieceColor();

    boolean isOpponentOf(ChessPieceColor color);

    List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition from);

    ChessPieceSnapshot createSnapshot();

    void restoreSnapshot(ChessPieceSnapshot snapshot);
}