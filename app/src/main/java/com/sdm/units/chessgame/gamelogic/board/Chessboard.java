package com.sdm.units.chessgame.gamelogic.board;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public interface Chessboard {
    
    Chessboard deepCopy();

    void resetBoard();

    ChessboardOrientation getOrientation();

    List<ChessboardPosition> getOccupiedSquaresOf(ChessPieceColor color);

    boolean isUnoccupiedSquare(ChessboardPosition pos);

    Optional<ChessPiece> getPieceAt(ChessboardPosition position);

    boolean isOpponentAt(ChessPieceColor player, ChessboardPosition pos);

    void putPieceAt(ChessboardPosition position, ChessPiece piece);

    void removePieceAt(ChessboardPosition position);
}