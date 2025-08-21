package test.chessgame.gamelogic.testdoubles;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class PieceStub extends PieceFake {
    
    private final Set<ChessboardPosition> legalDestinations;

    public PieceStub(ChessPieceColor color, ChessPieceInfo info, Set<ChessboardPosition> legalDestinations) {
        super(color, info);
        this.legalDestinations = legalDestinations;
    }

    @Override
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition fromPosition) {
        return legalDestinations;
    }
}