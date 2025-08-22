package com.sdm.units.chessgame.gui.controller.interaction;

import com.sdm.units.chessgame.gui.controller.command.CommandFactory;
import com.sdm.units.chessgame.gui.controller.command.GameCommand;

public class ToolbarInteractionController {

    private final CommandFactory commandFactory;

    public ToolbarInteractionController(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public void undoLastMove() {
        executeCommand(commandFactory.createUndoCommand());
    }

    public void restart() {
        executeCommand(commandFactory.createRestartCommand());
    }

    public void close() {
        executeCommand(commandFactory.createEndGameCommand());
    }

    private void executeCommand(GameCommand command) {
        command.execute();
    }
}