package unitTest.chessgame.gamelogic.move.special.castling;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetector;
import com.sdm.units.chessgame.gamelogic.board.evaluation.PathSafetySimulator;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingPattern;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unitTest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;
import unitTest.chessgame.gamelogic.testdoubles.PieceFake;

@DisplayName("CastlingEligibility")
class CastlingEligibilityTest {

    private CastlingEligibility eligibility;
    private PathSafetySimulatorStub pathSafetySimulatorStub;
    private AttackDetectorStub attackDetectorStub;
    private CastlingPatternStub patternStub;

    private ChessboardFake board;
    private ChessPiece whiteKing;
    private ChessPiece whiteRook;
    private ChessboardPosition kingFrom;
    private ChessboardPosition rookFrom;
    private ChessboardPosition kingTo;
    private ChessboardPosition rookTo;
    private CastlingCandidate candidate;

    @BeforeEach
    void setUp() {
        pathSafetySimulatorStub = new PathSafetySimulatorStub();
        attackDetectorStub = new AttackDetectorStub();
        patternStub = new CastlingPatternStub();

        eligibility = new CastlingEligibility(pathSafetySimulatorStub, attackDetectorStub, patternStub);

        board = new ChessboardFake();

        whiteKing = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.KING);
        whiteRook = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);

        kingFrom = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
        kingTo = new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE);
        rookFrom = new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE);
        rookTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE);

        board.putPieceAt(kingFrom, whiteKing);
        board.putPieceAt(rookFrom, whiteRook);

        candidate = new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo, whiteKing, whiteRook);

        patternStub.setKingPath(List.of(
            new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE),
            kingTo
        ));
    }

    @Nested
    @DisplayName("when all conditions are met")
    class WhenAllConditionsMet {

        @Test
        @DisplayName("should return true for valid castling")
        void shouldReturnTrueForValidCastling() {
            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("when any condition fails")
    class WhenAnyConditionFails {

        @Test
        @DisplayName("should return false if king is not a king")
        void shouldReturnFalseIfKingIsNotAKing() {
            candidate = new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo,
                new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.QUEEN), whiteRook);

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }

        @Test
        @DisplayName("should return false if rook is not a rook")
        void shouldReturnFalseIfRookIsNotARook() {
            candidate = new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo,
                whiteKing, new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.BISHOP));

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }

        @Test
        @DisplayName("should return false if pieces have different colors")
        void shouldReturnFalseIfPiecesHaveDifferentColors() {
            candidate = new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo,
                whiteKing, new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.ROOK));

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }

        @Test
        @DisplayName("should return false if either piece has moved")
        void shouldReturnFalseIfEitherPieceHasMoved() {
            ChessPiece movedKing = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.KING);
            movedKing.markAsMoved();
            candidate = new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo, movedKing, whiteRook);

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }

        @Test
        @DisplayName("should return false if king is in check")
        void shouldReturnFalseIfKingIsInCheck() {
            attackDetectorStub.setUnderAttack(true);

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }

        @Test
        @DisplayName("should return false if path is not clear")
        void shouldReturnFalseIfPathIsNotClear() {
            board.putPieceAt(kingTo, new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN));

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }

        @Test
        @DisplayName("should return false if any intermediate square is unsafe for king")
        void shouldReturnFalseIfIntermediateSquareIsUnsafe() {
            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isTrue();

            pathSafetySimulatorStub.failOn(new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE));

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }
    }

    private static class PathSafetySimulatorStub implements PathSafetySimulator {

        private final Set<ChessboardPosition> failPositions = new HashSet<>();

        void failOn(ChessboardPosition pos) {
            failPositions.add(pos);
        }

        @Override
        public boolean isPathSafe(Chessboard board, ChessboardPosition from, List<ChessboardPosition> path, ChessPieceColor color) {
            for (ChessboardPosition pos : path) {
                if (failPositions.contains(pos)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class AttackDetectorStub implements AttackDetector {
        
        private boolean underAttack;

        void setUnderAttack(boolean underAttack) {
            this.underAttack = underAttack;
        }

        @Override
        public boolean isUnderAttack(Chessboard board, ChessPieceColor color) {
            return underAttack;
        }
    }

    private static class CastlingPatternStub extends CastlingPattern {
        
        private List<ChessboardPosition> path = List.of();

        void setKingPath(List<ChessboardPosition> path) {
            this.path = path;
        }

        @Override
        public List<ChessboardPosition> kingPathSquares(ChessboardPosition kingFrom, ChessboardPosition rookFrom) {
            return path;
        }
    }
}