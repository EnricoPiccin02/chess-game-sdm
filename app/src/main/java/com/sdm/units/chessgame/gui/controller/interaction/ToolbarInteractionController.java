package com.sdm.units.chessgame.gui.controller.interaction;

import com.sdm.units.chessgame.gui.controller.command.CommandFactory;
import com.sdm.units.chessgame.gui.controller.command.GameCommand;

public class ToolbarInteractionController implements ToolbarInteractionStrategy {

    private final CommandFactory commandFactory;

    public ToolbarInteractionController(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
    
    @Override
    public void restart() {
        executeCommand(commandFactory.createRestartCommand());
    }
    
    @Override
    public void undo() {
        executeCommand(commandFactory.createUndoCommand());
    }

    @Override
    public void exit() {
        executeCommand(commandFactory.createEndGameCommand());
    }

    private void executeCommand(GameCommand command) {
        command.execute();
    }
}