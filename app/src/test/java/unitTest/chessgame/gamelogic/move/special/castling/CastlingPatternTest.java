package unitTest.chessgame.gamelogic.move.special.castling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingPattern;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unitTest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("CastlingPattern")
class CastlingPatternTest {

    private CastlingPattern pattern;
    private ChessboardFake board;
    private ChessPiece whiteKing;
    private ChessPiece whiteRookKingside;
    private ChessPiece whiteRookQueenside;
    private ChessboardPosition whiteKingFrom;
    private ChessboardPosition whiteRookFromKingside;
    private ChessboardPosition whiteRookFromQueenside;
    private ChessboardPosition whiteKingToKingside; 
    private ChessboardPosition whiteKingToQueenside;
    private ChessboardPosition whiteRookToKingside; 
    private ChessboardPosition whiteRookToQueenside;
    private CastlingCandidate whiteCastlingKingside;
    private CastlingCandidate whiteCastlingQeenside;

    @BeforeEach
    void setUp() {
        pattern = new CastlingPattern();
        board = new ChessboardFake();
        whiteKing = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.KING);
        whiteRookKingside = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);
        whiteRookQueenside = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);

        whiteKingFrom = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
        whiteRookFromKingside = new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE);
        whiteRookFromQueenside = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);

        whiteKingToKingside = new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE);
        whiteKingToQueenside = new ChessboardPosition(ChessboardFile.C, ChessboardRank.ONE);
        whiteRookToKingside = new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE);
        whiteRookToQueenside = new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE);

        whiteCastlingKingside = new CastlingCandidate(whiteKingFrom, whiteKingToKingside, whiteRookFromKingside, whiteRookToKingside, whiteKing, whiteRookKingside);
        whiteCastlingQeenside = new CastlingCandidate(whiteKingFrom, whiteKingToQueenside, whiteRookFromQueenside, whiteRookToQueenside, whiteKing, whiteRookQueenside);
    }

    @Nested
    @DisplayName("findCandidates")
    class FindCandidates {

        @Test
        @DisplayName("should return empty when king and rook are missing")
        void shouldReturnEmptyWhenKingAndRookMissing() {
            List<CastlingCandidate> candidates = pattern.findCandidates(board, whiteKingFrom, ChessboardOrientation.WHITE_BOTTOM);
            assertThat(candidates).isEmpty();
        }

        @Test
        @DisplayName("should return empty when king is missing")
        void shouldReturnEmptyWhenKingMissing() {
            board.putPieceAt(whiteRookFromKingside, whiteRookQueenside);
            List<CastlingCandidate> candidates = pattern.findCandidates(board, whiteKingFrom, ChessboardOrientation.WHITE_BOTTOM);
            assertThat(candidates).isEmpty();
        }
        
        @Test
        @DisplayName("should return empty when rook is missing")
        void shouldReturnEmptyWhenRookMissing() {
            board.putPieceAt(whiteKingFrom, whiteKing);
            List<CastlingCandidate> candidates = pattern.findCandidates(board, whiteKingFrom, ChessboardOrientation.WHITE_BOTTOM);
            assertThat(candidates).isEmpty();
        }

        @Test
        @DisplayName("should return kingside castling candidate")
        void shouldReturnKingsideCandidate() {
            board.putPieceAt(whiteKingFrom, whiteKing);
            board.putPieceAt(whiteRookFromKingside, whiteRookKingside);

            List<CastlingCandidate> candidates = pattern.findCandidates(board, whiteKingFrom, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).hasSize(1);
            assertThat(candidates).containsExactly(whiteCastlingKingside);
        }

        @Test
        @DisplayName("should return queenside castling candidate")
        void shouldReturnQueensideCandidate() {
            board.putPieceAt(whiteKingFrom, whiteKing);
            board.putPieceAt(whiteRookFromQueenside, whiteRookQueenside);

            List<CastlingCandidate> candidates = pattern.findCandidates(board, whiteKingFrom, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(candidates).hasSize(1);
            assertThat(candidates).containsExactly(whiteCastlingQeenside);
        }

        @Test
        @DisplayName("should return both candidates when king and rook present in both sides")
        void shouldReturnBothCandidatesWhenKingAndRookPresentBothSides() {
            board.putPieceAt(whiteKingFrom, whiteKing);
            board.putPieceAt(whiteRookFromKingside, whiteRookKingside);
            board.putPieceAt(whiteRookFromQueenside, whiteRookQueenside);
    
            List<CastlingCandidate> candidates = pattern.findCandidates(board, whiteKingFrom, ChessboardOrientation.WHITE_BOTTOM);
    
            assertThat(candidates).hasSize(2);
            assertThat(candidates).containsExactlyInAnyOrder(whiteCastlingKingside, whiteCastlingQeenside);
        }
    }

    @Nested
    @DisplayName("buildCandidate")
    class BuildCandidate {

        @Test
        @DisplayName("should return empty when no matching candidate")
        void shouldReturnEmptyWhenNoMatchingCandidate() {
            board.putPieceAt(whiteKingFrom, whiteKing);
            board.putPieceAt(whiteRookFromQueenside, whiteRookQueenside);

            ChessboardPosition nonMatchingKingTo = new ChessboardPosition(ChessboardFile.E, ChessboardRank.THREE);
            Optional<CastlingCandidate> result = pattern.buildCandidate(board, whiteKingFrom, nonMatchingKingTo);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("should return kingside candidate when kingTo matches")
        void shouldReturnKingsideCandidateWhenKingToMatches() {
            board.putPieceAt(whiteKingFrom, whiteKing);
            board.putPieceAt(whiteRookFromKingside, whiteRookKingside);

            Optional<CastlingCandidate> result = pattern.buildCandidate(board, whiteKingFrom, whiteKingToKingside);

            assertThat(result).isPresent();
            CastlingCandidate candidate = result.get();
            assertEquals(whiteCastlingKingside, candidate);
        }

        @Test
        @DisplayName("should return queenside candidate when kingTo matches")
        void shouldReturnQueensideCandidateWhenKingToMatches() {
            board.putPieceAt(whiteKingFrom, whiteKing);
            board.putPieceAt(whiteRookFromQueenside, whiteRookQueenside);

            Optional<CastlingCandidate> result = pattern.buildCandidate(board, whiteKingFrom, whiteKingToQueenside);

            assertThat(result).isPresent();
            CastlingCandidate candidate = result.get();
            assertEquals(whiteCastlingQeenside, candidate);
        }
    }

    @Nested
    @DisplayName("kingPathSquares")
    class KingPathSquares {

        @Test
        @DisplayName("should return empty list for invalid rook position")
        void shouldReturnEmptyForInvalidRookPosition() {
            ChessboardPosition invalidRookPos = new ChessboardPosition(ChessboardFile.B, ChessboardRank.ONE);
            List<ChessboardPosition> path = pattern.kingPathSquares(whiteKingFrom, invalidRookPos);
            assertThat(path).isEmpty();
        }

        @Test
        @DisplayName("should return correct path for kingside castling")
        void shouldReturnCorrectPathForKingsideCastling() {
            List<ChessboardPosition> path = pattern.kingPathSquares(whiteKingFrom, whiteRookFromKingside);
            assertThat(path).isEqualTo(List.of(
                new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE),
                new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE)
            ));
        }

        @Test
        @DisplayName("should return correct path for queenside castling")
        void shouldReturnCorrectPathForQueensideCastling() {
            List<ChessboardPosition> path = pattern.kingPathSquares(whiteKingFrom, whiteRookFromQueenside);
            assertThat(path).isEqualTo(List.of(
                new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE),
                new ChessboardPosition(ChessboardFile.C, ChessboardRank.ONE)
            ));
        }
    }
}