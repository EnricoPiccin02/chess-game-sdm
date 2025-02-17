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
        if (!pieceIsSelected) {
            // First click: Select the piece if movable
            if (gameLogic.isMovable(File, Rank)) {
                chessBoardView.highlightSquare(File, Rank);
                pieceIsSelected = true;
                selectedPieceFile = File;
                selectedPieceRank = Rank;
            }
        } else {
            if (selectedPieceFile == File && selectedPieceRank == Rank) {
                // Player clicked the same square (deselect)
                chessBoardView.clearHighlights();
                pieceIsSelected = false;
                selectedPieceFile = null;
                selectedPieceRank = null;
            } else {
                // Check if the move is valid
                if (!gameLogic.isValidMove(selectedPieceFile, selectedPieceRank, File, Rank)) {
                    // Invalid move: Clear highlights only once
                    chessBoardView.clearHighlights();
                    pieceIsSelected = false;
                    selectedPieceFile = null;
                    selectedPieceRank = null;
                }
                else{
                    chessBoardView.clearHighlights();
                    pieceIsSelected = false;
                    selectedPieceFile = null;
                    selectedPieceRank = null;
                }
            }
        }
    }

}
