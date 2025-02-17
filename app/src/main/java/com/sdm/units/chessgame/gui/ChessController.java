package com.sdm.units.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.GameLogic;
import com.sdm.units.chessgame.gui.ChessBoardView;

public class ChessController{
    private final ChessBoardView chessBoardView;
    private final GameLogic gameLogic;
    private ChessboardPosition selectedPosition;
    public ChessController(ChessBoardView chessBoardView, GameLogic gameLogic) {
        this.chessBoardView = chessBoardView;
        this.gameLogic = gameLogic;
    }

    public void handleSquareClick(ChessboardPosition position) {
        if (selectedPosition == null) {
            handleFirstClick(position);
        } else {
            handleSecondClick(position);
        }
    }

    private void handleFirstClick(ChessboardPosition position) {
        if (gameLogic.isMovable(position)) {
            selectPiece(position);
        }
    }

    private void handleSecondClick(ChessboardPosition position) {
        if (position.equals(selectedPosition)) {
            deselectPiece();
            return;
        }

        if (gameLogic.isValidMove(selectedPosition, position)) {
            makeMove(position);
        } else {
            deselectPiece();
        }
    }

    private void selectPiece(ChessboardPosition position) {
        chessBoardView.highlightSquare(position);
        selectedPosition = position;
    }

    private void deselectPiece() {
        chessBoardView.clearHighlights();
        selectedPosition = null;
    }

    private void makeMove(ChessboardPosition targetPosition) {
        gameLogic.makeMove(selectedPosition, targetPosition);
        chessBoardView.updateBoard();
        deselectPiece();
    }

    public GameLogic getGameLogic() { return gameLogic;
    }

    public ChessBoardView getChessBoardView() { return chessBoardView;
    }
}