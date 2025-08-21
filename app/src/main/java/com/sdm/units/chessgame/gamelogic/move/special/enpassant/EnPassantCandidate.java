package com.sdm.units.chessgame.gamelogic.move.special.enpassant;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public record EnPassantCandidate(
    ChessboardPosition from,
    ChessboardPosition to,
    ChessboardPosition capturingPosition,
    ChessPiece movingPawn,
    ChessPiece capturedPawn
) {}