package com.sdm.units.chessgame.gui.controller.interaction;

public class ToolbarInteractionController {

    private final InteractionContext interactionContext;

    public ToolbarInteractionController(InteractionContext interactionContext) {
        this.interactionContext = interactionContext;
    }

    public void undoLastMove() {
        interactionContext.executeCommand(interactionContext.createUndoCommand());
    }

    public void restart() {
        interactionContext.executeCommand(interactionContext.createRestartCommand());
    }

    public void close() {
        interactionContext.executeCommand(interactionContext.createEndGameCommand());
    }
}