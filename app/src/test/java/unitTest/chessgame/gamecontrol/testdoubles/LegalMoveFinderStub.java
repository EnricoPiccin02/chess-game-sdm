package unittest.chessgame.gamecontrol.testdoubles;

import java.util.Optional;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.state.LegalMoveFinder;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public class LegalMoveFinderStub implements LegalMoveFinder {
    
    private Set<ChessboardPosition> destinations = Set.of();
    private Optional<ReversibleMove> move = Optional.empty();

    public void setDestinations(Set<ChessboardPosition> destinations) {
        this.destinations = destinations;
    }

    public void setMove(Optional<ReversibleMove> move) {
        this.move = move;
    }

    @Override
    public Set<ChessboardPosition> findLegalDestinations(Chessboard board, ChessboardPosition from) {
        return destinations;
    }

    @Override
    public Optional<ReversibleMove> createIfValid(Chessboard board, ChessboardPosition from, ChessboardPosition to) {
        return move;
    }
}