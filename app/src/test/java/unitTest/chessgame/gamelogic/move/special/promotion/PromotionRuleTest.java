package unittest.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPattern;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionRule;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("PromotionRule")
class PromotionRuleTest {

    private PromotionPatternStub patternStub;
    private PromotionEligibilityStub eligibilityStub;
    private PromotionMoveFactoryStub factoryStub;

    private PromotionRule rule;
    private ChessboardFake board;

    private ChessboardPosition from;
    private ChessboardPosition to;
    private ChessPiece pawnDummy;
    private ChessPiece capturedPieceDummy;
    private ReversibleMove dummyMove;

    @BeforeEach
    void setUp() {
        pawnDummy = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        capturedPieceDummy = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);

        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
        to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);

        board = new ChessboardFake();
        board.putPieceAt(from, pawnDummy);

        dummyMove = Mockito.mock(ReversibleMove.class);

        patternStub = new PromotionPatternStub();
        eligibilityStub = new PromotionEligibilityStub();
        factoryStub = new PromotionMoveFactoryStub(dummyMove);

        rule = new PromotionRule(patternStub, eligibilityStub, factoryStub);
    }

    @Nested
    @DisplayName("generateMovesFrom")
    class GenerateMovesFrom {

        @Test
        @DisplayName("should generate promotion move when candidate is valid")
        void shouldGeneratePromotionMoveWhenCandidateIsValid() {
            PromotionCandidate candidate = new PromotionCandidate(from, to, pawnDummy, Optional.empty());
            patternStub.setFindCandidates(List.of(candidate));
            eligibilityStub.setCanExecute(true);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(moves).hasSize(1);
            assertThat(moves).containsExactly(dummyMove);
            assertSame(factoryStub.getLastCreatedFrom(), candidate);
        }

        @Test
        @DisplayName("should not generate move when eligibility check fails")
        void shouldNotGenerateMoveWhenEligibilityFails() {
            patternStub.setFindCandidates(List.of(new PromotionCandidate(from, to, pawnDummy, Optional.empty())));;
            eligibilityStub.setCanExecute(false);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(moves).isEmpty();
        }
    }

    @Nested
    @DisplayName("validateAndCreate")
    class ValidateAndCreate {

        @Test
        @DisplayName("should create promotion move for normal promotion")
        void shouldCreatePromotionMoveForNormalPromotion() {
            PromotionCandidate candidate = new PromotionCandidate(from, to, pawnDummy, Optional.empty());
            patternStub.setBuildCandidate(Optional.of(candidate));
            eligibilityStub.setCanExecute(true);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from, to, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(maybeMove).isPresent();
            assertThat(maybeMove).contains(dummyMove);
            assertSame(factoryStub.getLastCreatedFrom(), candidate);
        }

        @Test
        @DisplayName("should create promotion move for capture promotion")
        void shouldCreatePromotionMoveForCapturePromotion() {
            PromotionCandidate candidate = new PromotionCandidate(from, to, pawnDummy, Optional.of(capturedPieceDummy));
            board.putPieceAt(to, capturedPieceDummy);
            patternStub.setBuildCandidate(Optional.of(candidate));
            eligibilityStub.setCanExecute(true);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from, to, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(maybeMove).isPresent();
            assertThat(maybeMove).contains(dummyMove);
            assertSame(factoryStub.getLastCreatedFrom(), candidate);
        }

        @Test
        @DisplayName("should return empty when pattern returns no candidate")
        void shouldReturnEmptyWhenNoCandidate() {
            patternStub.setBuildCandidate(Optional.empty());

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from, to, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(maybeMove).isEmpty();
        }

        @Test
        @DisplayName("should return empty when eligibility check fails")
        void shouldReturnEmptyWhenEligibilityFails() {
            patternStub.setBuildCandidate(Optional.of(
                new PromotionCandidate(from, to, pawnDummy, Optional.empty())
            ));
            eligibilityStub.setCanExecute(false);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, from, to, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(maybeMove).isEmpty();
        }
    }

    @Test
    @DisplayName("should have high priority")
    void shouldHaveHighPriority() {
        assertThat(rule.getPriority()).isEqualTo(MoveRulePriority.HIGH_PRIORITY.getPriority());
    }

    private final class PromotionPatternStub extends PromotionPattern {
        
        private List<PromotionCandidate> foundCandidates = List.of();
        private Optional<PromotionCandidate> builtCandidate = Optional.empty();

        void setFindCandidates(List<PromotionCandidate> candidates) {
            this.foundCandidates = candidates;
        }

        void setBuildCandidate(Optional<PromotionCandidate> candidate) {
            this.builtCandidate = candidate;
        }

        @Override
        public List<PromotionCandidate> findCandidates(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
            return foundCandidates;
        }

        @Override
        public Optional<PromotionCandidate> buildCandidate(Chessboard board, ChessboardPosition from, ChessboardPosition to) {
            return builtCandidate;
        }
    }

    private final class PromotionEligibilityStub extends PromotionEligibility {
        
        private boolean canExecute;

        void setCanExecute(boolean canExecute) {
            this.canExecute = canExecute;
        }

        @Override
        public boolean canExecute(Chessboard board, PromotionCandidate candidate, ChessboardOrientation orientation) {
            return canExecute;
        }
    }

    private final class PromotionMoveFactoryStub extends PromotionMoveFactory {
        
        private PromotionCandidate lastCreatedFrom;
        private ReversibleMove moveToReturn;

        PromotionMoveFactoryStub(ReversibleMove move) {
            super(null);
            this.moveToReturn = move;
        }

        PromotionCandidate getLastCreatedFrom() {
            return lastCreatedFrom;
        }

        @Override
        public ReversibleMove create(PromotionCandidate candidate) {
            lastCreatedFrom = candidate;
            return moveToReturn;
        }
    }
}