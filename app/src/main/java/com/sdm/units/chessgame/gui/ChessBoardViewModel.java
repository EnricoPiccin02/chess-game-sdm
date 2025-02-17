package com.sdm.units.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardPosition;

import java.util.List;

// View Model to represent the board state
public record ChessBoardViewModel(
        List<SquareViewModel> squares
) {
    public record SquareViewModel(
            ChessboardPosition position,
            PieceViewModel piece
    ) {}

    public record PieceViewModel(
            String symbol,
            boolean isWhite
    ) {}
}