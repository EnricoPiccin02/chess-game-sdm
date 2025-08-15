package test.chessgame.gamelogic.move.special.enpassant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantPattern;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantRule;

import test.chessgame.gamelogic.testdoubles.ChessboardFake;
import test.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("EnPassantRule")
class EnPassantRuleTest {

    private EnPassantCandidate candidate;
    private EnPassantRule rule;

    private EnPassantPatternStub patternStub;
    private EnPassantEligibilityStub eligibilityStub;
    private EnPassantMoveFactoryStub factoryStub;

    private ChessboardFake board;
    private ChessboardPosition from;
    private ChessboardPosition to;
    private PieceDummy movingPawnDummy;
    private PieceDummy capturedPawnDummy;
    private ReversibleMove dummyMove;

    @BeforeEach
    void setUp() {
        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);
        to = new ChessboardPosition(ChessboardFile.F, ChessboardRank.SIX);

        movingPawnDummy = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        capturedPawnDummy = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.PAWN);
        board = new ChessboardFake();
        board.putPieceAt(from, movingPawnDummy);

        dummyMove = Mockito.mock(ReversibleMove.class);

        patternStub = new EnPassantPatternStub();
        eligibilityStub = new EnPassantEligibilityStub();
        factoryStub = new EnPassantMoveFactoryStub(dummyMove);

        candidate = new EnPassantCandidate(from, to, new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE), movingPawnDummy, capturedPawnDummy);
        rule = new EnPassantRule(patternStub, eligibilityStub, factoryStub);
    }

    @Nested
    @DisplayName("generateMovesFrom")
    class GenerateMovesFrom {

        @Test
        @DisplayName("should return moves when pattern matches and eligibility is true")
        void shouldReturnMovesWhenPatternMatchesAndEligibilityTrue() {
            patternStub.setFindCandidates(List.of(candidate));
            eligibilityStub.setCanExecute(true);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(moves).hasSize(1);
            assertThat(moves).containsExactly(dummyMove);
            assertSame(factoryStub.getLastCreatedFrom(), candidate);
        }

        @Test
        @DisplayName("should return empty when no candidates are found")
        void shouldReturnEmptyWhenNoCandidatesFound() {
            patternStub.setFindCandidates(List.of());

            List<ReversibleMove> moves = rule.generateMovesFrom(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(moves).isEmpty();
        }

        @Test
        @DisplayName("should filter out ineligible candidates")
        void shouldFilterOutIneligibleCandidates() {
            patternStub.setFindCandidates(List.of(candidate));
            eligibilityStub.setCanExecute(false);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(moves).isEmpty();
        }
    }

    @Nested
    @DisplayName("validateAndCreate")
    class ValidateAndCreate {

        @Test
        @DisplayName("should return move when candidate is eligible")
        void shouldReturnMoveWhenCandidateIsEligible() {
            patternStub.setBuildCandidate(Optional.of(candidate));
            eligibilityStub.setCanExecute(true);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from, to, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(maybeMove).isPresent();
            assertThat(maybeMove).contains(dummyMove);
            assertSame(factoryStub.getLastCreatedFrom(), candidate);
        }

        @Test
        @DisplayName("should return empty when no candidate is built")
        void shouldReturnEmptyWhenNoCandidateBuilt() {
            patternStub.setBuildCandidate(Optional.empty());

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from, to, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(maybeMove).isEmpty();
        }

        @Test
        @DisplayName("should return empty when candidate is ineligible")
        void shouldReturnEmptyWhenCandidateIsIneligible() {
            patternStub.setBuildCandidate(Optional.of(candidate));
            eligibilityStub.setCanExecute(false);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from, to, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(maybeMove).isEmpty();
        }
    }

    @Test
    @DisplayName("should have medium priority")
    void shouldHaveMediumPriority() {
        assertThat(rule.getPriority()).isEqualTo(MoveRulePriority.MEDIUM_PRIORITY.getPriority());
    }

    private final class EnPassantPatternStub extends EnPassantPattern {
        
        private List<EnPassantCandidate> foundCandidates = List.of();
        private Optional<EnPassantCandidate> builtCandidate = Optional.empty();

        void setFindCandidates(List<EnPassantCandidate> candidates) {
            this.foundCandidates = candidates;
        }

        void setBuildCandidate(Optional<EnPassantCandidate> candidate) {
            this.builtCandidate = candidate;
        }

        @Override
        public List<EnPassantCandidate> findCandidates(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
            return foundCandidates;
        }

        @Override
        public Optional<EnPassantCandidate> buildCandidate(Chessboard board, ChessboardPosition from, ChessboardPosition to) {
            return builtCandidate;
        }
    }

    private final class EnPassantEligibilityStub extends EnPassantEligibility {
        
        private boolean canExecute;

        public EnPassantEligibilityStub() {
            super(null);
        }

        void setCanExecute(boolean canExecute) {
            this.canExecute = canExecute;
        }

        @Override
        public boolean canExecute(Chessboard board, EnPassantCandidate candidate, ChessboardOrientation orientation) {
            return canExecute;
        }
    }

    private final class EnPassantMoveFactoryStub extends EnPassantMoveFactory {
        
        private EnPassantCandidate lastCreatedFrom;
        private ReversibleMove moveToReturn;

        EnPassantMoveFactoryStub(ReversibleMove move) {
            this.moveToReturn = move;
        }

        EnPassantCandidate getLastCreatedFrom() {
            return lastCreatedFrom;
        }

        @Override
        public ReversibleMove create(EnPassantCandidate candidate) {
            lastCreatedFrom = candidate;
            return moveToReturn;
        }
    }
}