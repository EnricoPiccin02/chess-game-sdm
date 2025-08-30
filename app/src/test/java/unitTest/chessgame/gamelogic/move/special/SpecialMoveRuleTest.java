package unittest.chessgame.gamelogic.move.special;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveRule;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.SpecialMoveEligibilityStub;
import unittest.chessgame.gamelogic.testdoubles.SpecialMoveFactoryStub;
import unittest.chessgame.gamelogic.testdoubles.SpecialMovePatternStub;

public abstract class SpecialMoveRuleTest<CandidateT> {

    protected SpecialMovePatternStub<CandidateT> patternStub;
    protected SpecialMoveEligibilityStub<CandidateT> eligibilityStub;
    protected SpecialMoveFactoryStub<CandidateT> factoryStub;
    protected SpecialMoveRule<CandidateT> rule;
    protected Chessboard board;
    protected ReversibleMove dummyMove;

    @BeforeEach
    void setupBase() {
        board = new ChessboardFake();
        dummyMove = Mockito.mock(ReversibleMove.class);

        patternStub = new SpecialMovePatternStub<>();
        eligibilityStub = new SpecialMoveEligibilityStub<>();
        factoryStub = new SpecialMoveFactoryStub<>(dummyMove);
    }

    @Nested
    @DisplayName("generating moves from a given candidate")
    class GeneratingMoves {

        @Test
        @DisplayName("should generate move from eligible candidate")
        void shouldGenerateMoveFromEligibleCandidate() {
            CandidateT candidate = createValidCandidate();
            patternStub.setFindCandidates(List.of(candidate));
            eligibilityStub.setCanExecute(true);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, from(), orientation());

            assertThat(moves).containsExactly(dummyMove);
        }

        @Test
        @DisplayName("should not generate move from ineligible candidate")
        void shouldNotGenerateMoveFromIneligibleCandidate() {
            CandidateT candidate = createValidCandidate();
            patternStub.setFindCandidates(List.of(candidate));
            eligibilityStub.setCanExecute(false);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, from(), orientation());

            assertThat(moves).isEmpty();
        }
    }

    @Nested
    @DisplayName("validating candidates and creating moves")
    class ValidateAndCreate {

        @Test
        @DisplayName("should create move from eligible candidate")
        void shouldCreateMoveFromEligibleCandidate() {
            CandidateT candidate = createValidCandidate();
            patternStub.setBuildCandidate(Optional.of(candidate));
            eligibilityStub.setCanExecute(true);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from(), to(), orientation());

            assertThat(maybeMove).contains(dummyMove);
        }

        @Test
        @DisplayName("should not create any move when candidate ineligible")
        void shouldNotCreateAnyMoveWhenCandidateIneligible() {
            CandidateT candidate = createValidCandidate();
            patternStub.setBuildCandidate(Optional.of(candidate));
            eligibilityStub.setCanExecute(false);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from(), to(), orientation());

            assertThat(maybeMove).isEmpty();
        }
    }

    protected abstract CandidateT createValidCandidate();
    
    protected abstract ChessboardPosition from();
    
    protected abstract ChessboardPosition to();
    
    protected abstract ChessboardOrientation orientation();
}