package unittest.chessgame.gamecontrol.acceptance;

import static com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor.*;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.*;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.*;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.Bishop;
import com.sdm.units.chessgame.gamelogic.pieces.King;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;

public class ChessboardFactoryHelper {

    public Chessboard startingBoard() {
        Chessboard board = new ChessboardFake();
        
        putPawns(board);
        putRooks(board);
        putKnights(board);
        putBishops(board);
        putQueens(board);
        putKings(board);
        
        return board;
    }

    private void putPawns(Chessboard board) {
        for (ChessboardFile file : ChessboardFile.values()) {
            board.putPieceAt(new ChessboardPosition(file, TWO), new Pawn(WHITE, ChessboardOrientation.WHITE_BOTTOM));
            board.putPieceAt(new ChessboardPosition(file, SEVEN), new Pawn(BLACK, ChessboardOrientation.WHITE_BOTTOM));
        }
    }

    private void putRooks(Chessboard board) {
        board.putPieceAt(new ChessboardPosition(A, ONE), new Rook(WHITE));
        board.putPieceAt(new ChessboardPosition(H, ONE), new Rook(WHITE));
        board.putPieceAt(new ChessboardPosition(A, EIGHT), new Rook(BLACK));
        board.putPieceAt(new ChessboardPosition(H, EIGHT), new Rook(BLACK));
    }

    private void putKnights(Chessboard board) {
        board.putPieceAt(new ChessboardPosition(B, ONE), new Knight(WHITE));
        board.putPieceAt(new ChessboardPosition(G, ONE), new Knight(WHITE));
        board.putPieceAt(new ChessboardPosition(B, EIGHT), new Knight(BLACK));
        board.putPieceAt(new ChessboardPosition(G, EIGHT), new Knight(BLACK));
    }

    private void putBishops(Chessboard board) {
        board.putPieceAt(new ChessboardPosition(C, ONE), new Bishop(WHITE));
        board.putPieceAt(new ChessboardPosition(F, ONE), new Bishop(WHITE));
        board.putPieceAt(new ChessboardPosition(C, EIGHT), new Bishop(BLACK));
        board.putPieceAt(new ChessboardPosition(F, EIGHT), new Bishop(BLACK));
    }

    private void putQueens(Chessboard board) {
        board.putPieceAt(new ChessboardPosition(D, ONE), new Queen(WHITE));
        board.putPieceAt(new ChessboardPosition(D, EIGHT), new Queen(BLACK));
    }

    private void putKings(Chessboard board) {
        board.putPieceAt(new ChessboardPosition(E, ONE), new King(WHITE));
        board.putPieceAt(new ChessboardPosition(E, EIGHT), new King(BLACK));
    }
}