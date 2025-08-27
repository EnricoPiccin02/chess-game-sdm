package com.sdm.units.chessgame.gamecontrol.state;

import com.sdm.units.chessgame.gamecontrol.flow.EventFactory;
import com.sdm.units.chessgame.gamecontrol.flow.ScoreKeeper;
import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;

public record GameStateAssembly(
    TurnManager turns,
    ScoreKeeper scores,
    EventFactory notifier
) {}