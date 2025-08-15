package test.chessgame.gamelogic.testdoubles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class ChessboardFake implements Chessboard {

    private final Map<ChessboardPosition, ChessPiece> pieces = new HashMap<>();
    
    public ChessboardFake() {}

    @Override
    public Chessboard deepCopy() {
        return Mockito.mock(Chessboard.class);
    }

    @Override
    public void resetBoard() {
        pieces.clear();
    }

    @Override
    public ChessboardOrientation getOrientation() {
        return Mockito.mock(ChessboardOrientation.class);
    }

    @Override
    public List<ChessboardPosition> getOccupiedSquaresOf(ChessPieceColor color) {
        return List.of();
    }

    @Override
    public boolean isUnoccupiedSquare(ChessboardPosition pos) {
        return !pieces.containsKey(pos);
    }

    @Override
    public Optional<ChessPiece> getPieceAt(ChessboardPosition position) {
        return Optional.ofNullable(pieces.get(position));
    }

    @Override
    public boolean isOpponentAt(ChessPieceColor player, ChessboardPosition pos) {
        return false;
    }

    @Override
    public void putPieceAt(ChessboardPosition position, ChessPiece piece) {
        pieces.put(position, piece);
    }

    @Override
    public void removePieceAt(ChessboardPosition position) {
        pieces.remove(position);
    }
}