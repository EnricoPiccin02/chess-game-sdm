package com.sdm.units.chessgame.gui.controller.command;

import com.sdm.units.chessgame.gamecontrol.model.domain.ChessGameController;

public class EndGameCommand implements GameCommand {

    private final ChessGameController controller;

    public EndGameCommand(ChessGameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.endGame();
    }
}