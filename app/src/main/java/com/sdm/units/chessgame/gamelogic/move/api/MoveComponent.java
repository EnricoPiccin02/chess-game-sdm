package com.sdm.units.chessgame.gamelogic.move.api;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public record MoveComponent(ChessboardPosition from, ChessboardPosition to) { }