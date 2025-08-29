package com.sdm.units.chessgame.gui.controller.command;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;

public class UndoCommand implements GameCommand {

    private final InteractionContext context;
    private final GameStateController controller;

    public UndoCommand(InteractionContext context, GameStateController controller) {
        this.context = context;
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.undoMove();
        context.initialize();
    }
}