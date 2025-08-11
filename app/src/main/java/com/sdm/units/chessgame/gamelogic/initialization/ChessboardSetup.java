package com.sdm.units.chessgame.gamelogic.initialization;

import java.util.HashMap;
import java.util.Map;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class ChessboardSetup {

    private final ChessPiecePlacementStrategy strategy;

    public ChessboardSetup(ChessPiecePlacementStrategy strategy) {
        this.strategy = strategy;
    }

    public Map<ChessboardPosition, ChessPiece> generate(ChessboardOrientation orientation) {
        Map<ChessboardPosition, ChessPiece> board = new HashMap<>();

        for (ChessPieceColor color : ChessPieceColor.values()) {
            board.putAll(strategy.initialize(color, orientation));
        }

        return board;
    }
}