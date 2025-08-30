package unittest.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPattern;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;
import unittest.chessgame.gamelogic.testdoubles.PieceStub;

@DisplayName("PromotionPattern")
class PromotionPatternTest {

    private PromotionPattern promotionPattern;
    private Chessboard board;

    @BeforeEach
    void setUp() {
        promotionPattern = new PromotionPattern();
        board = new ChessboardFake();
    }

    @Nested
    @DisplayName("when finding promotion candidates")
    class FindCandidates {

        private ChessboardPosition from;
        private ChessboardPosition to;
        private ChessPiece pawnStub;

        @BeforeEach
        void setUp() {
            from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            to = new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT);
            pawnStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, Set.of(to));
        }

        @Test
        @DisplayName("should detect promotion move when pawn advances into last rank")
        void shouldDetectPromotionMoveWhenPawnAdvancesIntoLastRank() {
            board.putPieceAt(from, pawnStub);

            List<PromotionCandidate> candidates = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).hasSize(1);
        }

        @Test
        @DisplayName("should record source square of promotion candidate")
        void shouldRecordSourceSquareOfPromotionCandidate() {
            board.putPieceAt(from, pawnStub);

            PromotionCandidate candidate = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM).get(0);

            assertThat(candidate.from()).isEqualTo(from);
        }

        @Test
        @DisplayName("should record target square of promotion candidate")
        void shouldRecordTargetSquareOfPromotionCandidate() {
            board.putPieceAt(from, pawnStub);

            PromotionCandidate candidate = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM).get(0);

            assertThat(candidate.to()).isEqualTo(to);
        }

        @Test
        @DisplayName("should record moving pawn in promotion candidate")
        void shouldRecordMovingPawnInPromotionCandidate() {
            board.putPieceAt(from, pawnStub);

            PromotionCandidate candidate = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM).get(0);

            assertThat(candidate.movingPawn()).isEqualTo(pawnStub);
        }

        @Test
        @DisplayName("should not include captured piece when promotion is a quiet move")
        void shouldNotIncludeCapturedPieceWhenPromotionIsQuietMove() {
            board.putPieceAt(from, pawnStub);

            PromotionCandidate candidate = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM).get(0);

            assertThat(candidate.capturedPiece()).isEmpty();
        }

        @Test
        @DisplayName("should not find any candidates when no piece at given position")
        void shouldNotFindAnyCandidatesWhenNoPieceAtGivenPosition() {
            from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);

            List<PromotionCandidate> candidates = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).isEmpty();
        }

        @Test
        @DisplayName("should include captured piece when promotion captures enemy")
        void shouldIncludeCapturedPieceWhenPromotionCapturesEnemy() {
            ChessPiece capturedDummy = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);

            board.putPieceAt(from, pawnStub);
            board.putPieceAt(to, capturedDummy);

            PromotionCandidate candidate = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM).get(0);

            assertThat(candidate.capturedPiece()).contains(capturedDummy);
        }
    }

    @Nested
    @DisplayName("when building promotion candidates")
    class BuildCandidate {

        private ChessboardPosition from;
        private ChessboardPosition to;

        @BeforeEach
        void setUp() {
            from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
        }

        @Test
        @DisplayName("should build promotion candidate when there is a piece that can be promoted")
        void shouldBuildPromotionCandidateWhenPromotingPieceExists() {
            ChessPiece pawnStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, Set.of(to));
            board.putPieceAt(from, pawnStub);

            Optional<PromotionCandidate> result = promotionPattern.buildCandidate(board, from, to);

            assertThat(result).isPresent();
        }

        @Test
        @DisplayName("should not build any candidate when no piece at correct position")
        void shouldNotBuildAnyCandidateWhenNoPieceAtCorrectPosition() {
            Optional<PromotionCandidate> result = promotionPattern.buildCandidate(board, from, to);

            assertThat(result).isEmpty();
        }
    }
}