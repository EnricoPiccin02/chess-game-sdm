package com.sdm.units.chessgame.gamecontrol.assembler.gui;

import com.sdm.units.chessgame.gui.board.view.ChessboardView;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardInteractionController;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;

public record ChessboardAssembly(
    ChessboardView view,
    ChessboardInteractionController controller,
    InteractionContext context
) {}