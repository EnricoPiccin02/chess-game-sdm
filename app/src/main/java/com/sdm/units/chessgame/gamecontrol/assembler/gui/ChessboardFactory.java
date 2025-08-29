package com.sdm.units.chessgame.gamecontrol.assembler.gui;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;
import com.sdm.units.chessgame.gui.board.square.BorderHighlightRenderer;
import com.sdm.units.chessgame.gui.board.view.ChessboardPanel;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardInteractionController;
import com.sdm.units.chessgame.gui.controller.interaction.DefaultSquareInteractionManager;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;
import com.sdm.units.chessgame.gui.controller.interaction.SquareInteractionManager;
import com.sdm.units.chessgame.gui.pieces.ChessViewConfigurator;

public final class ChessboardFactory {

    private ChessboardFactory() {}

    public static ChessboardAssembly create(MoveQuery moveQuery, GameStateController controller) {
        ChessboardView chessboardView = new ChessboardPanel(ChessViewConfigurator.createPieceViewFactory(), new BorderHighlightRenderer());

        SquareInteractionManager interactionManager = new DefaultSquareInteractionManager();
        ChessboardInteractionController boardController = new ChessboardInteractionController(chessboardView, interactionManager);

        InteractionContext interactionContext = new InteractionContext(moveQuery, controller, boardController);

        boardController.addClickHandler(interactionContext::handleSquareClick);

        return new ChessboardAssembly(chessboardView, boardController, interactionContext);
    }
}