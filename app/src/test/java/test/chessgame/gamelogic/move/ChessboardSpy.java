package test.chessgame.gamelogic.move;

import java.util.ArrayList;
import java.util.List;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class ChessboardSpy extends ChessboardFake {

    private final List<String> calls = new ArrayList<>();

    @Override
    public void putPieceAt(ChessboardPosition position, ChessPiece piece) {
        calls.add("put:" + position + ":" + piece);
        super.putPieceAt(position, piece);
    }

    @Override
    public void removePieceAt(ChessboardPosition position) {
        calls.add("remove:" + position);
        super.removePieceAt(position);
    }

    public boolean wasPutCalledWith(ChessboardPosition pos, ChessPiece piece) {
        return calls.contains("put:" + pos + ":" + piece);
    }

    public boolean wasRemoveCalledWith(ChessboardPosition pos) {
        return calls.contains("remove:" + pos);
    }
}
