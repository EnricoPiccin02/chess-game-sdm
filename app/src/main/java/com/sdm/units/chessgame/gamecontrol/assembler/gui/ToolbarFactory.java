package com.sdm.units.chessgame.gamecontrol.assembler.gui;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gui.controller.command.CommandFactory;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionController;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionStrategy;

public final class ToolbarFactory {

    public static ToolbarInteractionStrategy create(InteractionContext interactionContext, GameStateController controller) {
        CommandFactory commandFactory = new CommandFactory(interactionContext, controller);
        return new ToolbarInteractionController(commandFactory);
    }
}