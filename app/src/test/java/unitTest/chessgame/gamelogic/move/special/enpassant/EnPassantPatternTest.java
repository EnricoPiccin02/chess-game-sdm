package unittest.chessgame.gamelogic.move.special.enpassant;

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

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

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
    @DisplayName("when finding en passant candidates")
    class FindCandidates {

        @Test
        @DisplayName("should allow capture of left adjacent pawn")
        void shouldAllowCaptureOfLeftAdjacentPawn() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition captured = new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE);

            board.putPieceAt(from, whitePawn);
            board.putPieceAt(captured, blackPawn);

            List<EnPassantCandidate> candidates = pattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).extracting(EnPassantCandidate::capturingPosition)
                .containsExactly(captured);
        }

        @Test
        @DisplayName("should allow capture of right adjacent pawn")
        void shouldAllowCaptureOfRightAdjacentPawn() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition captured = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);

            board.putPieceAt(from, whitePawn);
            board.putPieceAt(captured, blackPawn);

            List<EnPassantCandidate> candidates = pattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).extracting(EnPassantCandidate::capturingPosition)
                .containsExactly(captured);
        }

        @Test
        @DisplayName("should not find any candidates when no pawn is adjacent")
        void shouldNotFindAnyCandidatesWhenNoPawnIsAdjacent() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            board.putPieceAt(from, whitePawn);

            List<EnPassantCandidate> candidates = pattern.findCandidates(board, from, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).isEmpty();
        }
    }

    @Nested
    @DisplayName("when building en passant candidate")
    class BuildCandidate {

        @Test
        @DisplayName("should create candidate when target landing position is vacant")
        void shouldCreateCandidateWhenTargetLandingPositionIsVacant() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.C, ChessboardRank.SIX);
            ChessboardPosition captured = new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE);

            board.putPieceAt(from, whitePawn);
            board.putPieceAt(captured, blackPawn);

            Optional<EnPassantCandidate> candidate = pattern.buildCandidate(board, from, to);

            assertThat(candidate).isPresent();
        }

        @Test
        @DisplayName("should not create candidate when target landing position is occupied")
        void shouldNotCreateCandidateWhenTargetLandingPositionIsOccupied() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.C, ChessboardRank.SIX);

            board.putPieceAt(from, whitePawn);
            board.putPieceAt(to, new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.ROOK));

            Optional<EnPassantCandidate> candidate = pattern.buildCandidate(board, from, to);

            assertThat(candidate).isEmpty();
        }

        @Test
        @DisplayName("should not create candidate when move is not diagonal")
        void shouldNotCreateCandidateWhenMoveIsNotDiagonal() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.D, ChessboardRank.SIX);

            board.putPieceAt(from, whitePawn);

            Optional<EnPassantCandidate> candidate = pattern.buildCandidate(board, from, to);

            assertThat(candidate).isEmpty();
        }
    }
}