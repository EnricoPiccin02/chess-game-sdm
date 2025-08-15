package com.sdm.units.chessgame.gamelogic.move.special.castling;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public record CastlingCandidate(
    ChessboardPosition kingFrom,
    ChessboardPosition kingTo,
    ChessboardPosition rookFrom,
    ChessboardPosition rookTo,
    ChessPiece king,
    ChessPiece rook
) {}