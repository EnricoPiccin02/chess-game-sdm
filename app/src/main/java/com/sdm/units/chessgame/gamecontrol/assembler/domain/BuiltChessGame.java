package com.sdm.units.chessgame.gamecontrol.assembler.domain;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventPublisher;

public record BuiltChessGame(
    ChessGameEventPublisher eventPublisher,
    MoveQuery moveQuery,
    GameStateController controller
) {}