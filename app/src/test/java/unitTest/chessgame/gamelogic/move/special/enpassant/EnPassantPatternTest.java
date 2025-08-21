package unitTest.chessgame.gamelogic.move.special.enpassant;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantPattern;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unitTest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("EnPassantPattern")
class EnPassantPatternTest {

    private EnPassantPattern pattern;
    private ChessboardFake board;

    private ChessPiece whitePawn;
    private ChessPiece blackPawn;

    @BeforeEach
    void setUp() {
        pattern = new EnPassantPattern();
        board = new ChessboardFake();

        whitePawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        blackPawn = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.PAWN);
    }

    @Nested
    @DisplayName("findCandidates")
    class FindCandidates {

        @Test
        @DisplayName("should find en passant capture to the left")
        void shouldFindEnPassantToLeft() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition capturedPos = new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE);
            ChessboardPosition landingPos = new ChessboardPosition(ChessboardFile.C, ChessboardRank.SIX);

            board.putPieceAt(from, whitePawn);
            board.putPieceAt(capturedPos, blackPawn);

            List<EnPassantCandidate> candidates = pattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).hasSize(1);
            EnPassantCandidate candidate = candidates.get(0);
            assertThat(candidate.from()).isEqualTo(from);
            assertThat(candidate.capturingPosition()).isEqualTo(capturedPos);
            assertThat(candidate.to()).isEqualTo(landingPos);
            assertThat(candidate.movingPawn()).isEqualTo(whitePawn);
            assertThat(candidate.capturedPawn()).isEqualTo(blackPawn);
        }

        @Test
        @DisplayName("should find en passant capture to the right")
        void shouldFindEnPassantToRight() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition capturedPos = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);
            ChessboardPosition landingPos = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SIX);

            board.putPieceAt(from, whitePawn);
            board.putPieceAt(capturedPos, blackPawn);

            List<EnPassantCandidate> candidates = pattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).hasSize(1);
            EnPassantCandidate candidate = candidates.get(0);
            assertThat(candidate.from()).isEqualTo(from);
            assertThat(candidate.capturingPosition()).isEqualTo(capturedPos);
            assertThat(candidate.to()).isEqualTo(landingPos);
            assertThat(candidate.movingPawn()).isEqualTo(whitePawn);
            assertThat(candidate.capturedPawn()).isEqualTo(blackPawn);
        }

        @Test
        @DisplayName("should return empty when no adjacent pawn to capture")
        void shouldReturnEmptyWhenNoAdjacentPawn() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            board.putPieceAt(from, whitePawn);

            List<EnPassantCandidate> candidates = pattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).isEmpty();
        }
    }

    @Nested
    @DisplayName("buildCandidate")
    class BuildCandidate {

        @Test
        @DisplayName("should build candidate for valid diagonal empty target")
        void shouldBuildCandidateForValidDiagonalEmptyTarget() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.C, ChessboardRank.SIX);
            ChessboardPosition capturedPos = new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE);

            board.putPieceAt(from, whitePawn);
            board.putPieceAt(capturedPos, blackPawn);

            Optional<EnPassantCandidate> candidateOpt = pattern.buildCandidate(board, from, to);

            assertThat(candidateOpt).isPresent();
            EnPassantCandidate candidate = candidateOpt.get();
            assertThat(candidate.from()).isEqualTo(from);
            assertThat(candidate.to()).isEqualTo(to);
            assertThat(candidate.capturingPosition()).isEqualTo(capturedPos);
            assertThat(candidate.movingPawn()).isEqualTo(whitePawn);
            assertThat(candidate.capturedPawn()).isEqualTo(blackPawn);
        }

        @Test
        @DisplayName("should return empty when target square is not empty")
        void shouldReturnEmptyWhenTargetNotEmpty() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.C, ChessboardRank.SIX);
            ChessboardPosition capturedPos = new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE);

            board.putPieceAt(from, whitePawn);
            board.putPieceAt(capturedPos, blackPawn);
            board.putPieceAt(to, new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.ROOK));

            Optional<EnPassantCandidate> candidateOpt = pattern.buildCandidate(board, from, to);

            assertThat(candidateOpt).isEmpty();
        }

        @Test
        @DisplayName("should return empty when not diagonal move")
        void shouldReturnEmptyWhenNotDiagonalMove() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.D, ChessboardRank.SIX);

            board.putPieceAt(from, whitePawn);

            Optional<EnPassantCandidate> candidateOpt = pattern.buildCandidate(board, from, to);

            assertThat(candidateOpt).isEmpty();
        }
    }
}