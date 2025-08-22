package com.sdm.units.chessgame.gamecontrol.state;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public interface MoveQuery {

    Set<ChessboardPosition> selectable();

    Set<ChessboardPosition> legalDestinations(ChessboardPosition from);
}