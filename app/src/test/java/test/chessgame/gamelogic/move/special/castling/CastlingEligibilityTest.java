package test.chessgame.gamelogic.move.special.castling;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.CheckEvaluator;
import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingPattern;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import test.chessgame.gamelogic.testdoubles.ChessboardFake;
import test.chessgame.gamelogic.testdoubles.PieceDummy;
import test.chessgame.gamelogic.testdoubles.PieceStub;

@DisplayName("CastlingEligibility")
class CastlingEligibilityTest {

    private CastlingEligibility eligibility;
    private MoveValidatorStub moveValidatorStub;
    private CheckEvaluatorStub checkEvaluatorStub;
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
        moveValidatorStub = new MoveValidatorStub();
        checkEvaluatorStub = new CheckEvaluatorStub();
        patternStub = new CastlingPatternStub();

        eligibility = new CastlingEligibility(moveValidatorStub, checkEvaluatorStub, patternStub);

        board = new ChessboardFake();

        whiteKing = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.KING, List.of());
        whiteRook = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, List.of());

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
            moveValidatorStub.succeedOn(kingTo);
            moveValidatorStub.succeedOn(rookTo);
            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isTrue();
            assertThat(moveValidatorStub.succeedPositions).containsExactlyInAnyOrder(kingTo, rookTo);
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
            ChessPiece movedKing = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.KING, List.of());
            movedKing.markAsMoved();
            candidate = new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo, movedKing, whiteRook);

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }

        @Test
        @DisplayName("should return false if king is in check")
        void shouldReturnFalseIfKingIsInCheck() {
            checkEvaluatorStub.kingAt(kingFrom);
            checkEvaluatorStub.underAttackOn(kingFrom);

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

            checkEvaluatorStub.kingAt(new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE));
            checkEvaluatorStub.underAttackOn(new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE));

            assertThat(eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM)).isFalse();
        }
    }

    private static class MoveValidatorStub implements MoveValidator {

        private final Set<ChessboardPosition> succeedPositions = new HashSet<>();

        void succeedOn(ChessboardPosition pos) {
            succeedPositions.add(pos);
        }

        @Override
        public Optional<ReversibleMove> validateAndCreate(Chessboard board, ChessboardPosition from, ChessboardPosition to, ChessboardOrientation orientation) {

            if (!succeedPositions.contains(to)) {
                return Optional.empty();
            }

            return Optional.of(new ReversibleMove() {
                
                @Override
                public CaptureResult executeOn(Chessboard b) {
                    return CaptureResult.none();
                }

                @Override
                public CaptureResult undoOn(Chessboard b) {
                    return CaptureResult.none();
                }
                
                @Override
                public MoveComponent getPrimaryMoveComponent() {
                    throw new UnsupportedOperationException("getPrimaryMoveComponent not tested here");
                }
            });
        }
    }

    private static class CheckEvaluatorStub implements CheckEvaluator {
        
        private ChessboardPosition kingPosition;
        private final Set<ChessboardPosition> kingUnderAttackPositions = new HashSet<>();

        void kingAt(ChessboardPosition pos) {
            kingPosition = pos;
        }

        void underAttackOn(ChessboardPosition pos) {
            kingUnderAttackPositions.add(pos);
        }

        @Override
        public boolean isUnderAttack(Chessboard board, ChessPieceColor color) {
            return kingUnderAttackPositions.contains(kingPosition);
        }

        @Override
        public boolean isCheckmate(Chessboard board, ChessPieceColor defenderColor) {
            throw new UnsupportedOperationException("isCheckmate is not tested here");
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