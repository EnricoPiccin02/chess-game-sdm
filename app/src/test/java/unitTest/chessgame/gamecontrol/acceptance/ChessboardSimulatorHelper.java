package unittest.chessgame.gamecontrol.acceptance;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.*;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.*;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class ChessboardSimulatorHelper {

    private final Chessboard board;

    public ChessboardSimulatorHelper(Chessboard startingBoard) {
        this.board = startingBoard;
    }

    public Chessboard move(ChessboardPosition from, ChessboardPosition to) {
        board.getPieceAt(from).ifPresent(piece -> {
            board.removePieceAt(from);
            board.putPieceAt(to, piece);
        });
        return board;
    }

    public Chessboard enPassant(ChessboardPosition from, ChessboardPosition to) {
        board.removePieceAt(new ChessboardPosition(to.file(), from.rank()));
        return move(from, to);
    }

    public Chessboard castleKingside(ChessPieceColor color) {
        ChessboardRank castlingRank = color == ChessPieceColor.WHITE ? ONE : EIGHT;

        move(new ChessboardPosition(E, castlingRank), new ChessboardPosition(G, castlingRank));
        return move(new ChessboardPosition(H, castlingRank), new ChessboardPosition(F, castlingRank));
    }

    public Chessboard castleQueenside(ChessPieceColor color) {
        ChessboardRank castlingRank = color == ChessPieceColor.WHITE ? ONE : EIGHT;

        move(new ChessboardPosition(E, castlingRank), new ChessboardPosition(C, castlingRank));
        return move(new ChessboardPosition(A, castlingRank), new ChessboardPosition(D, castlingRank));
    }

    public Chessboard promote(ChessboardPosition from, ChessboardPosition to, ChessPiece promotedPiece) {
        board.putPieceAt(from, promotedPiece);
        return move(from, to);
    }
}