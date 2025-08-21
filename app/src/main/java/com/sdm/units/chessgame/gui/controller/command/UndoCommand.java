package com.sdm.units.chessgame.gui.controller.command;

import com.sdm.units.chessgame.gamecontrol.model.domain.ChessGameController;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;

public class UndoCommand implements GameCommand {

    private final InteractionContext context;
    private final ChessGameController controller;

    public UndoCommand(InteractionContext context, ChessGameController controller) {
        this.context = context;
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.undoLastMove();
        context.resetToStartState();
    }
}