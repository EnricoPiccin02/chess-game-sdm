package com.sdm.units.chessgame.gamelogic.board.state;

import java.util.Optional;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public interface Chessboard {

    void resetBoard();

    ChessboardOrientation getOrientation();

    Set<ChessboardPosition> getOccupiedSquaresOf(ChessPieceColor color);

    boolean isUnoccupiedSquare(ChessboardPosition pos);

    Optional<ChessPiece> getPieceAt(ChessboardPosition position);

    boolean isOpponentAt(ChessPieceColor player, ChessboardPosition pos);

    void putPieceAt(ChessboardPosition position, ChessPiece piece);

    void removePieceAt(ChessboardPosition position);
}