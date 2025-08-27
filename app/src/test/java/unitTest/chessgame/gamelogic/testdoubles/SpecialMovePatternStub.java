package unittest.chessgame.gamelogic.testdoubles;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMovePattern;

public class SpecialMovePatternStub<CandidateT> implements SpecialMovePattern<CandidateT> {

    private List<CandidateT> foundCandidates = List.of();
    private Optional<CandidateT> builtCandidate = Optional.empty();

    @Override
    public List<CandidateT> findCandidates(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
        return foundCandidates;
    }

    @Override
    public Optional<CandidateT> buildCandidate(Chessboard board, ChessboardPosition from, ChessboardPosition to) {
        return builtCandidate;
    }

    public void setFindCandidates(List<CandidateT> candidates) {
        this.foundCandidates = candidates;
    }

    public void setBuildCandidate(Optional<CandidateT> candidate) {
        this.builtCandidate = candidate;
    }
}