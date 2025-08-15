package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public record PromotionCandidate(
    ChessboardPosition from,
    ChessboardPosition to,
    ChessPiece movingPawn,
    Optional<ChessPiece> capturedPiece
) {}