package com.sdm.units.chessgame.gui;


import com.sdm.units.chessgame.gamelogic.GameLogic;

public class ChessController {

    private GameLogic gameLogic;
    private ChessBoardView chessBoardView;

    public ChessController(GameLogic gameLogic, ChessBoardView chessBoardView) {
        this.gameLogic = gameLogic;
        this.chessBoardView = chessBoardView;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public ChessBoardView getChessBoardView() {
        return chessBoardView;
    }
}
