package com.sdm.units.chessgame.gui.controller.command;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;

public class CommandFactory {
    
    private final InteractionContext context;
    private final GameStateController controller;

    public CommandFactory(InteractionContext context, GameStateController controller) {
        this.context = context;
        this.controller = controller;
    }

    public GameCommand createUndoCommand() {
        return new UndoCommand(context, controller);
    }

    public GameCommand createRestartCommand() {
        return new RestartCommand(context, controller);
    }

    public GameCommand createEndGameCommand() {
        return new EndGameCommand(controller);
    }
}