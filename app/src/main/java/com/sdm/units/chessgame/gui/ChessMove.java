package com.sdm.units.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.pieces.ChessPiece;

import java.util.Optional;

public record ChessMove(ChessboardPosition from,
                   ChessboardPosition to,
                   Optional<ChessPiece> piece) {}
