package com.sdm.units.chessgame.gamecontrol;

import com.sdm.units.chessgame.gamecontrol.assembler.domain.BuiltChessGame;
import com.sdm.units.chessgame.gamecontrol.assembler.domain.ChessGameControllerBuilder;
import com.sdm.units.chessgame.gamecontrol.assembler.gui.ChessGameWindowBuilder;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.DefaultPromotionPieceSelector;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;

public final class ChessGameInitializer {
    
    public static void main(String[] args) {
        BuiltChessGame builtGame = defaultGame();
        ChessGameView gameView = defaultView(builtGame);

        GameStateController controller = builtGame.controller();
        controller.start();
        gameView.initialize();
    }

    private static BuiltChessGame defaultGame() {
        return ChessGameControllerBuilder.create()
            .withOrientation(ChessboardOrientation.WHITE_BOTTOM)
            .withPromotionSelector(new DefaultPromotionPieceSelector())
            .build();
    }

    private static ChessGameView defaultView(BuiltChessGame builtGame) {
        return ChessGameWindowBuilder.create()
            .withGame(builtGame)
            .build();
    }
}