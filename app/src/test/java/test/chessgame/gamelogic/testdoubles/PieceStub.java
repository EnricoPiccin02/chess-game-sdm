package test.chessgame.gamelogic.testdoubles;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;

public class PieceStub implements ChessPiece {

    private boolean moved;
    private final ChessPieceColor color;
    private final ChessPieceInfo info;
    private ChessPiece copy;
    private ChessPieceSnapshot snapshot;
    private final List<ChessboardPosition> legalMoves;

    public PieceStub(ChessPieceColor color, ChessPieceInfo info, List<ChessboardPosition> legalMoves) {
        this.color = color;
        this.info = info;
        this.legalMoves = legalMoves;
    }

    public PieceStub(ChessPieceColor color, ChessPieceInfo info, List<ChessboardPosition> legalMoves, ChessPiece copy, ChessPieceSnapshot snapshot) {
        this(color, info, legalMoves);
        this.copy = copy;
        this.snapshot = snapshot;
    }

    @Override
    public ChessPiece copy() {
        return copy;
    }

    @Override
    public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition fromPosition) {
        return legalMoves;
    }

    @Override
    public void markAsMoved() {
        moved = true;
    }

    @Override
    public boolean hasMoved() {
        return moved;
    }

    @Override
    public ChessPieceInfo pieceInfo() {
        return info;
    }

    @Override
    public ChessPieceColor pieceColor() {
        return color;
    }

    @Override
    public boolean isOpponentOf(ChessPieceColor color) {
        return true;
    }

    @Override
    public ChessPieceSnapshot createSnapshot() {
        return snapshot;
    }

    @Override
    public void restoreSnapshot(ChessPieceSnapshot snapshot) { }
}