package com.sdm.units.chessgame;

import java.util.Map;
import java.util.Objects;

import com.sdm.units.chessgame.gamelogic.Chessboard;
import com.sdm.units.chessgame.gamelogic.ChessboardMovementHandler;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;

public class ChessGamePlayer {
    
    protected final Chessboard chessboard;
    protected final ChessboardMovementHandler handler;
    protected final ChessGameView view;
    protected Map<ChessPieceColor, Integer> playerScore;
    protected ChessPieceColor currentPlayer;

    public ChessGamePlayer(ChessGameView view) {
        chessboard = new Chessboard();
        handler = new ChessboardMovementHandler();
        this.view = view;
    }
    
    public void start() {
        currentPlayer = ChessPieceColor.WHITE;
        view.display();
        view.setChessboard(chessboard);
        view.setChoosablePieces(chessboard.getNonVacantPositionsOfColor(currentPlayer));
    }

    public void playerChoosesMoves(ChessboardPosition fromPosition) {
        chessboard.getNonVacantPositionsOfColor(currentPlayer).stream()
            .filter(position -> Objects.equals(position, fromPosition))
            .findFirst()
            .ifPresent(position -> view.setChoosableMoves(handler.getPieceValidMoves(chessboard, position)));
    }

    public void playerMoves(ChessboardPosition fromPosition, ChessboardPosition toPosition) throws IllegalArgumentException {
        handler.makeMove(chessboard, fromPosition, toPosition).ifPresent(capturedPieceValue -> playerScore.put(currentPlayer, capturedPieceValue));
        view.setChessboard(chessboard);

        if (chessboard.getNonVacantPositionsOfColor(currentPlayer).isEmpty())
            view.displayMessage("Player " + currentPlayer + " won!");

        currentPlayer = ChessPieceColor.getOppositeColor(currentPlayer);
        view.setChoosablePieces(chessboard.getNonVacantPositionsOfColor(currentPlayer));
    }

    public void close() {
        view.close();
    }
}