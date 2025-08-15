package test.chessgame.gamelogic.move.special.promotion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPattern;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import test.chessgame.gamelogic.testdoubles.ChessboardFake;
import test.chessgame.gamelogic.testdoubles.PieceDummy;
import test.chessgame.gamelogic.testdoubles.PieceStub;

@DisplayName("PromotionPattern")
class PromotionPatternTest {

    private PromotionPattern promotionPattern;
    private ChessboardFake board;

    @BeforeEach
    void setUp() {
        promotionPattern = new PromotionPattern();
        board = new ChessboardFake();
    }

    @Nested
    @DisplayName("findCandidates")
    class FindCandidates {

        @Test
        @DisplayName("should find promotion candidate when pawn has legal moveTo promotion rank")
        void shouldFindPromotionCandidateWhenPawnHasLegalMoveToPromotionRank() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT);
            ChessPiece pawnStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, List.of(to));

            board.putPieceAt(from, pawnStub);

            List<PromotionCandidate> candidates = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertEquals(1, candidates.size());
            PromotionCandidate candidate = candidates.get(0);
            assertEquals(from, candidate.from());
            assertEquals(to, candidate.to());
            assertEquals(pawnStub, candidate.movingPawn());
            assertTrue(candidate.capturedPiece().isEmpty());
        }

        @Test
        @DisplayName("should return empty when no piece at given position")
        void shouldReturnEmptyWhenNoPieceAtGivenPosition() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);

            List<PromotionCandidate> candidates = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertTrue(candidates.isEmpty());
        }

        @Test
        @DisplayName("should include captured piece when promotion captures enemy")
        void shouldIncludeCapturedPieceWhenPromotionCapturesEnemy() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT);
            ChessPiece pawnStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, List.of(to));
            ChessPiece capturedDummy = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);

            board.putPieceAt(from, pawnStub);
            board.putPieceAt(to, capturedDummy);

            List<PromotionCandidate> candidates = promotionPattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertEquals(1, candidates.size());
            assertTrue(candidates.get(0).capturedPiece().isPresent());
            assertEquals(capturedDummy, candidates.get(0).capturedPiece().get());
        }
    }

    @Nested
    @DisplayName("buildCandidate")
    class BuildCandidate {

        @Test
        @DisplayName("should build promotion candidate when piece exists at from")
        void shouldBuildPromotionCandidateWhenPieceExistsAtFrom() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            ChessPiece pawnStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, List.of(to));

            board.putPieceAt(from, pawnStub);

            Optional<PromotionCandidate> result = promotionPattern.buildCandidate(board, from, to);

            assertTrue(result.isPresent());
            PromotionCandidate candidate = result.get();
            assertEquals(from, candidate.from());
            assertEquals(to, candidate.to());
            assertEquals(pawnStub, candidate.movingPawn());
        }

        @Test
        @DisplayName("shouldReturnEmptyWhenNoPieceAtFromPosition")
        void shouldReturnEmptyWhenNoPieceAtFromPosition() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);

            Optional<PromotionCandidate> result = promotionPattern.buildCandidate(board, from, to);

            assertTrue(result.isEmpty());
        }
    }
}