package com.sdm.units.chessgame;

import javax.swing.SwingUtilities;

import com.sdm.units.chessgame.gamelogic.Chessboard;
import com.sdm.units.chessgame.gamelogic.ChessboardMovementHandler;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.initialization.PawnPositionInitializer;
import com.sdm.units.chessgame.gui.ChessboardFrame;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.King;
import com.sdm.units.chessgame.pieces.Knight;
import com.sdm.units.chessgame.pieces.Pawn;
import com.sdm.units.chessgame.pieces.Queen;
import com.sdm.units.chessgame.pieces.Rook;

public class Game {
    public String getGreeting() {
        return "Hello World!";
    }
    public static void main(String[] args) {
        // System.out.println(new Game().getGreeting());
        // SwingUtilities.invokeLater(ChessboardFrame::createAndShowGUI);

        // Chessboard chessboard = new Chessboard();
        // System.out.println(chessboard.printBoard());

        // ChessPiece whitePawn = new Knight(ChessPieceColor.WHITE);
        // whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO)).forEach(System.out::println);

        // ChessboardMovementHandler handler = new ChessboardMovementHandler(new Chessboard());
        // handler.getPieceValidMoves(new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO)).forEach(System.out::println);
    }
}