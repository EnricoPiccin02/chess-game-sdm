package com.sdm.units.chessgame.gamecontrol.model.domain;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public interface ChessGameController {

    void startGame();

    Set<ChessboardPosition> getSelectablePositions();

    Set<ChessboardPosition> getLegalDestinationsFrom(ChessboardPosition from);

    void makeMove(ChessboardPosition from, ChessboardPosition to);

    void undoLastMove();

    void endGame();
}