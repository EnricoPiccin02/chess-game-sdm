package unittest.chessgame.gamelogic.testdoubles;

import java.util.ArrayList;
import java.util.List;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class ChessboardSpy extends ChessboardFake {
    
    private record PutCall(ChessboardPosition pos, ChessPiece piece) {}
    private record RemoveCall(ChessboardPosition pos) {}
    private final List<Object> calls = new ArrayList<>();

    @Override
    public void putPieceAt(ChessboardPosition position, ChessPiece piece) {
        calls.add(new PutCall(position, piece));
        super.putPieceAt(position, piece);
    }

    @Override
    public void removePieceAt(ChessboardPosition position) {
        calls.add(new RemoveCall(position));
        super.removePieceAt(position);
    }

    public boolean wasPutCalledWith(ChessboardPosition pos, ChessPiece piece) {
        return calls.contains(new PutCall(pos, piece));
    }

    public boolean wasRemoveCalledWith(ChessboardPosition pos) {
        return calls.contains(new RemoveCall(pos));
    }
}