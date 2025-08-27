package unittest.chessgame.gamelogic.testdoubles;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;

public class PieceDummy implements ChessPiece {

    private final ChessPieceColor color;
    private final ChessPieceInfo info;

    public PieceDummy(ChessPieceColor color, ChessPieceInfo info) {
        this.color = color;
        this.info = info;
    }

    @Override
    public void markAsMoved() {
        throw new UnsupportedOperationException("Dummy should not be asked for 'markAsMoved'");
    }

    @Override
    public boolean hasMoved() {
        throw new UnsupportedOperationException("Dummy should not be asked for 'hasMoved'");
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
        throw new UnsupportedOperationException("Dummy should not be asked for 'isOpponentOf'");
    }

    @Override
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition from) {
        throw new UnsupportedOperationException("Dummy should not be asked for 'getLegalDestinations'");
    }

    @Override
    public ChessPieceSnapshot createSnapshot() {
        throw new UnsupportedOperationException("Dummy should not be asked for 'createSnapshot'");
    }

    @Override
    public void restoreSnapshot(ChessPieceSnapshot snapshot) {
        throw new UnsupportedOperationException("Dummy should not be asked for 'restoreSnapshot'");
    }
}