package test.chessgame.gamelogic;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.movement.MovementStrategy;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;

public class ChessPieceStub extends ChessPiece {

    private List<ChessboardPosition> legalMoves = List.of();
    private boolean snapshotRestored = false;

    public ChessPieceStub(ChessPieceColor color, ChessPieceInfo info) {
        super(color, info, new StubStrategy());
    }

    @Override
    public void restoreSnapshot(ChessPieceSnapshot snapshot) {
        super.restoreSnapshot(snapshot);
        snapshotRestored = true;
    }

    @Override
    public ChessPiece copy() {
        return null;
    }

    public boolean isSnapshotRestored() {
        return snapshotRestored;
    }

    public void setLegalMoves(List<ChessboardPosition> moves) {
        this.legalMoves = moves;
    }

    @Override
    public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition fromPosition) {
        return legalMoves;
    }
    
    static class StubStrategy implements MovementStrategy {

        @Override
        public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
            return List.of();
        }
    }
}