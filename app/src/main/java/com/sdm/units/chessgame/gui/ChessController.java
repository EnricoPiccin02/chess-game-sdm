package com.sdm.units.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.*;
import com.sdm.units.chessgame.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sdm.units.chessgame.gamelogic.ChessPieceInfo.*;

public class ChessController{
    private final ChessBoardView chessBoardView;
    private final GameLogic gameLogic;
    private ChessboardPosition selectedPosition;
    public ChessController(ChessBoardView chessBoardView, GameLogic gameLogic) {
        this.chessBoardView = chessBoardView;
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameLogic() { return gameLogic;
    }

    public ChessBoardView getChessBoardView() { return chessBoardView;
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
        // if same square is selected reset selection
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
        updateView();
        deselectPiece();
    }

    public void updateView() {
        chessBoardView.updateBoard(createViewModel());
    }

    private ChessBoardViewModel createViewModel() {
        List<ChessBoardViewModel.SquareViewModel> squares = new ArrayList<>();

        for (ChessboardFile file : ChessboardFile.values()) {
            for (ChessboardRank rank : ChessboardRank.values()) {
                ChessboardPosition position = new ChessboardPosition(file, rank);
                squares.add(createSquareViewModel(position));
            }
        }

        return new ChessBoardViewModel(squares);
    }

    private ChessBoardViewModel.SquareViewModel createSquareViewModel(ChessboardPosition position) {
        Optional<ChessPiece> piece = gameLogic.getPieceAt(position);
        return new ChessBoardViewModel.SquareViewModel(
                position,
                piece.map(this::createPieceViewModel).orElse(null)
        );
    }

    private ChessBoardViewModel.PieceViewModel createPieceViewModel(ChessPiece piece) {
        return new ChessBoardViewModel.PieceViewModel(
                getPieceSymbol(piece),
                piece.color == ChessPieceColor.WHITE
        );
    }

    private String getPieceSymbol(ChessPiece piece) {
        return "♙";
//        return switch (piece) {
//            case KING -> "♔";
//            case QUEEN -> "♕";
//            case ROOK -> "♖";
//            case BISHOP -> "♗";
//            case KNIGHT -> "♘";
//            case PAWN -> "♙";
//        };
    }


}