package com.sdm.units.chessgame.gamecontrol.assembler.gui;

import java.util.EnumMap;

import com.sdm.units.chessgame.gamecontrol.assembler.domain.BuiltChessGame;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.ChessClockPanel;
import com.sdm.units.chessgame.gui.board.clock.PlayerClocksFactory;
import com.sdm.units.chessgame.gui.board.square.BorderHighlightRenderer;
import com.sdm.units.chessgame.gui.board.view.ChessGameFrame;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;
import com.sdm.units.chessgame.gui.board.view.ChessboardPanel;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;
import com.sdm.units.chessgame.gui.board.view.SwingDispatchingChessGameView;
import com.sdm.units.chessgame.gui.controller.command.CommandFactory;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventPublisher;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardInteractionController;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionController;
import com.sdm.units.chessgame.gui.pieces.ChessViewConfigurator;

public final class ChessGameWindowBuilder {

    private MoveQuery moveQuery;
    private GameStateController controller;
    private ChessGameEventPublisher publisher;
    private InteractionContext interactionContext;

    private ChessGameWindowBuilder() { }

    public static ChessGameWindowBuilder create() {
        return new ChessGameWindowBuilder();
    }

    public ChessGameWindowBuilder withGame(BuiltChessGame builtGame) {
        this.moveQuery = builtGame.moveQuery();
        this.controller = builtGame.controller();
        this.publisher = builtGame.eventPublisher();
        return this;
    }
    
    public ChessGameView build() {
        ChessboardView chessboardView = new ChessboardPanel(
            ChessViewConfigurator.createViewRegistry(),
            new BorderHighlightRenderer()
        );

        ChessboardInteractionController boardController = new ChessboardInteractionController(chessboardView);
        interactionContext = new InteractionContext(moveQuery, controller, boardController);
        boardController.setClickHandler(interactionContext::handleSquareClick);

        CommandFactory commandFactory = new CommandFactory(interactionContext, controller);
        ToolbarInteractionController toolbarController = new ToolbarInteractionController(commandFactory);
        
        PlayerClocksFactory clockFactory = new PlayerClocksFactory(300000); // 5 min
        EnumMap<ChessPieceColor, ChessClock> clocks = clockFactory.createClocks();

        PlayerClockPanelFactory clockPanelFactory = new PlayerClockPanelFactory(controller);
        EnumMap<ChessPieceColor, ChessClockPanel> clockPanels = clockPanelFactory.createPanels(clocks);

        ChessGameFrame frame = ChessGameFrameFactory.create(chessboardView, clockPanels, toolbarController);

        publisher.addChessGameEventListener(frame);

        interactionContext.resetToStartState();
        return new SwingDispatchingChessGameView(frame);
    }
}