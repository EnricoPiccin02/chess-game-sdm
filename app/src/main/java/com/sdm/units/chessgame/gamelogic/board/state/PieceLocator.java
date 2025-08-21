package com.sdm.units.chessgame.gamelogic.board.state;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

@FunctionalInterface
public interface PieceLocator {

    Optional<ChessboardPosition> locateFirstOf(Chessboard board, ChessPieceColor color);
}