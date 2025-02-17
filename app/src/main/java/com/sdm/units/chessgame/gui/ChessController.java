package com.sdm.units.chessgame.gui;


import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.GameLogic;

public class ChessController {

    private final GameLogic gameLogic;
    private final ChessBoardView chessBoardView;
    private boolean pieceIsSelected = false;

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

    public void handleSquareClick(ChessboardFile chessboardFile, ChessboardRank chessboardRank) {
        if (!pieceIsSelected){
            if(gameLogic.isMovable(chessboardFile,chessboardRank)){
                chessBoardView.highlightSquare(chessboardFile,chessboardRank);
                pieceIsSelected = true;
            }
        }
    }
}
