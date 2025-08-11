package test.chessgame.gamelogic.move;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.initialization.ChessboardSetup;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class ChessboardFake extends Chessboard {

    private final Map<ChessboardPosition, ChessPiece> pieces = new HashMap<>();
    
    public ChessboardFake() {
        super(ChessboardOrientation.WHITE_BOTTOM, Mockito.mock(ChessboardSetup.class));
    }

    @Override
    public void putPieceAt(ChessboardPosition pos, ChessPiece piece) {
        pieces.put(pos, piece);
    }
    
    @Override
    public Optional<ChessPiece> getPieceAt(ChessboardPosition pos) {
        return Optional.ofNullable(pieces.get(pos));
    }

    @Override
    public void removePieceAt(ChessboardPosition pos) { 
        pieces.remove(pos);
    }
}