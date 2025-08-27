package com.sdm.units.chessgame.gamecontrol.assembler.domain;

import com.sdm.units.chessgame.gamecontrol.flow.GameFlowService;
import com.sdm.units.chessgame.gamecontrol.state.BoardAssembly;
import com.sdm.units.chessgame.gamecontrol.state.GameStateAssembly;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.GameStateHandler;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;
import com.sdm.units.chessgame.gamecontrol.state.MoveQueryService;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;

public final class ChessGameControllerBuilder {

    private ChessboardOrientation orientation;
    private PromotionPieceSelector selector;

    private ChessGameControllerBuilder() {}

    public static ChessGameControllerBuilder create() {
        return new ChessGameControllerBuilder();
    }

    public ChessGameControllerBuilder withOrientation(ChessboardOrientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public ChessGameControllerBuilder withPromotionSelector(PromotionPieceSelector selector) {
        this.selector = selector;
        return this;
    }

    public BuiltChessGame build() {
        ChessDomainFactory domainFactory = new ChessDomainFactory(orientation, selector);
        BoardAssembly chessboardComponents = domainFactory.createChessboardComponents();
        GameStateAssembly gameStateComponents = domainFactory.createGameStateComponents();
        
        GameFlowService flowController = new GameFlowService(
            gameStateComponents.scores(),
            gameStateComponents.turns(),
            gameStateComponents.notifier()
        );

        MoveQuery queryService = new MoveQueryService(
            chessboardComponents.board(),
            chessboardComponents.moveFinder(),
            gameStateComponents.turns()
        );

        GameStateController stateHandler = new GameStateHandler(
            chessboardComponents.board(),
            chessboardComponents.executor(),
            chessboardComponents.moveFinder(),
            gameStateComponents.turns(),
            chessboardComponents.outcome(),
            flowController
        );

        return new BuiltChessGame(flowController, queryService, stateHandler);
    }
}