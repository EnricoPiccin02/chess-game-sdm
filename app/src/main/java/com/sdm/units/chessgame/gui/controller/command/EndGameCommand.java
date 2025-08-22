package com.sdm.units.chessgame.gui.controller.command;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;

public class EndGameCommand implements GameCommand {

    private final GameStateController controller;

    public EndGameCommand(GameStateController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.end();
    }
}