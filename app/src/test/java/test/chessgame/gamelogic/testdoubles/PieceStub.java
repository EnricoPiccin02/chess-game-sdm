package test.chessgame.gamelogic.testdoubles;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;

public class PieceStub implements ChessPiece {

    private boolean moved;
    private final ChessPieceColor color;
    private final ChessPieceInfo info;
    private ChessPieceSnapshot snapshot;
    private final Set<ChessboardPosition> legalDestinations;

    public PieceStub(ChessPieceColor color, ChessPieceInfo info, Set<ChessboardPosition> legalDestinations) {
        this.color = color;
        this.info = info;
        this.legalDestinations = legalDestinations;
    }

    public PieceStub(ChessPieceColor color, ChessPieceInfo info, Set<ChessboardPosition> legalDestinations, ChessPieceSnapshot snapshot) {
        this(color, info, legalDestinations);
        this.snapshot = snapshot;
    }

    @Override
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition fromPosition) {
        return legalDestinations;
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