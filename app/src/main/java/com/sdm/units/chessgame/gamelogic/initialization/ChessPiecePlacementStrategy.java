package com.sdm.units.chessgame.gamelogic.initialization;

import java.util.Map;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

@FunctionalInterface
public interface ChessPiecePlacementStrategy {

    Map<ChessboardPosition, ChessPiece> initialize(ChessPieceColor color, ChessboardOrientation orientation);
}