package com.sdm.units.chessgame.gui;


import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.GameLogic;

public class ChessController {

    private final GameLogic gameLogic;
    private final ChessBoardView chessBoardView;
    private boolean pieceIsSelected = false;
    private ChessboardFile selectedPieceFile;
    private ChessboardRank selectedPieceRank;

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

    public void handleSquareClick(ChessboardFile File, ChessboardRank Rank) {
        if (!pieceIsSelected){
            if(gameLogic.isMovable(File,Rank)){
                chessBoardView.highlightSquare(File,Rank);
                pieceIsSelected = true;
                selectedPieceFile = File;
                selectedPieceRank = Rank;
            }
        }
        else {
            if (selectedPieceFile == File && selectedPieceRank == Rank) {
                chessBoardView.unhighlightSquare(File,Rank);
                pieceIsSelected = false;
                selectedPieceFile = null;
                selectedPieceRank = null;
            }
            }

        }
}
