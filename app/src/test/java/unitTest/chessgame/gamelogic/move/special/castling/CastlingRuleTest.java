package unittest.chessgame.gamelogic.move.special.castling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingPattern;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingRule;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("CastlingRule")
class CastlingRuleTest {

    private CastlingPatternStub patternStub;
    private CastlingEligibilityStub eligibilityStub;
    private CastlingMoveFactoryStub factoryStub;

    private CastlingRule rule;
    private ChessboardFake board;

    private ChessboardPosition kingFrom;
    private ChessboardPosition kingTo;
    private ChessboardPosition rookFrom;
    private ChessboardPosition rookTo;

    private ChessPiece king;
    private ChessPiece rook;
    private CastlingCandidate candidate;
    private ReversibleMove dummyMove;

    @BeforeEach
    void setUp() {
        king = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.KING);
        rook = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);

        kingFrom = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
        kingTo = new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE);
        rookFrom = new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE);
        rookTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE);

        board = new ChessboardFake();
        dummyMove = Mockito.mock(ReversibleMove.class);

        patternStub = new CastlingPatternStub();
        eligibilityStub = new CastlingEligibilityStub();
        factoryStub = new CastlingMoveFactoryStub(dummyMove);
        
        rule = new CastlingRule(patternStub, eligibilityStub, factoryStub);

        candidate = new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo, king, rook);
    }

    @Nested
    @DisplayName("generateMovesFrom")
    class GenerateMovesFrom {

        @Test
        @DisplayName("should return moves created from eligible candidates")
        void shouldReturnMovesCreatedFromEligibleCandidates() {
            board.putPieceAt(kingFrom, king);
            patternStub.setFindCandidates(List.of(candidate));
            eligibilityStub.setCanExecute(true);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, kingFrom, ChessboardOrientation.WHITE_BOTTOM);

            assertEquals(1, moves.size());
            assertThat(moves).containsExactly(dummyMove);
            assertSame(factoryStub.getLastCreatedFrom(), candidate);
        }

        @Test
        @DisplayName("should return empty list when no candidates found")
        void shouldReturnEmptyWhenNoCandidates() {
            patternStub.setFindCandidates(List.of());
            eligibilityStub.setCanExecute(true);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, kingFrom, ChessboardOrientation.WHITE_BOTTOM);

            assertTrue(moves.isEmpty());
        }

        @Test
        @DisplayName("should return empty list when candidates are ineligible")
        void shouldReturnEmptyWhenCandidatesIneligible() {
            board.putPieceAt(kingFrom, king);
            patternStub.setFindCandidates(List.of(candidate));
            eligibilityStub.setCanExecute(false);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, kingFrom, ChessboardOrientation.WHITE_BOTTOM);

            assertTrue(moves.isEmpty());
        }
    }

    @Nested
    @DisplayName("validateAndCreate")
    class ValidateAndCreate {

        @Test
        @DisplayName("should return move created from eligible candidate")
        void shouldReturnMoveWhenCandidateEligible() {
            patternStub.setBuildCandidate(Optional.of(candidate));
            eligibilityStub.setCanExecute(true);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, kingFrom, kingTo, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(maybeMove).isPresent();
            assertThat(maybeMove).contains(dummyMove);
            assertSame(factoryStub.getLastCreatedFrom(), candidate);
        }

        @Test
        @DisplayName("should return empty when no candidate is built")
        void shouldReturnEmptyWhenNoCandidateBuilt() {
            patternStub.setBuildCandidate(Optional.empty());
            eligibilityStub.setCanExecute(true);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, kingFrom, kingTo, ChessboardOrientation.WHITE_BOTTOM);

            assertTrue(maybeMove.isEmpty());
        }

        @Test
        @DisplayName("should return empty when candidate is ineligible")
        void shouldReturnEmptyWhenCandidateIneligible() {
            patternStub.setBuildCandidate(Optional.of(candidate));
            eligibilityStub.setCanExecute(false);

            Optional<ReversibleMove> maybeMove = rule.validateAndCreate(board, kingFrom, kingTo, ChessboardOrientation.WHITE_BOTTOM);

            assertTrue(maybeMove.isEmpty());
        }
    }

    @Test
    @DisplayName("should have medium priority")
    void shouldHaveMediumPriority() {
        assertEquals(MoveRulePriority.MEDIUM_PRIORITY.getPriority(), rule.getPriority());
    }

    private final class CastlingPatternStub extends CastlingPattern {
        
        private List<CastlingCandidate> foundCandidates = List.of();
        private Optional<CastlingCandidate> builtCandidate = Optional.empty();

        void setFindCandidates(List<CastlingCandidate> candidates) {
            this.foundCandidates = candidates;
        }

        void setBuildCandidate(Optional<CastlingCandidate> candidate) {
            this.builtCandidate = candidate;
        }

        @Override
        public List<CastlingCandidate> findCandidates(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
            return foundCandidates;
        }

        @Override
        public Optional<CastlingCandidate> buildCandidate(Chessboard board, ChessboardPosition from, ChessboardPosition to) {
            return builtCandidate;
        }
    }

    private final class CastlingEligibilityStub extends CastlingEligibility {
        
        private boolean canExecute;

        CastlingEligibilityStub() {
            super(null, null, null);
        }

        void setCanExecute(boolean canExecute) {
            this.canExecute = canExecute;
        }

        @Override
        public boolean canExecute(Chessboard board, CastlingCandidate candidate, ChessboardOrientation orientation) {
            return canExecute;
        }
    }

    private final class CastlingMoveFactoryStub extends CastlingMoveFactory {
        
        private CastlingCandidate lastCreatedFrom;
        private ReversibleMove moveToReturn;

        CastlingMoveFactoryStub(ReversibleMove move) {
            this.moveToReturn = move;
        }

        CastlingCandidate getLastCreatedFrom() {
            return lastCreatedFrom;
        }

        @Override
        public ReversibleMove create(CastlingCandidate candidate) {
            lastCreatedFrom = candidate;
            return moveToReturn;
        }
    }
}