package unitTest.chessgame.gamelogic.move.special.enpassant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.MoveRecorder;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantEligibility;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unitTest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;
import unitTest.chessgame.gamelogic.testdoubles.PieceFake;

@DisplayName("EnPassantEligibility")
class EnPassantEligibilityTest {

    private EnPassantEligibility eligibility;
    private MoveRecorderStub moveRecorderStub;
    private ChessboardFake board;

    private ChessPiece whitePawnFake;
    private ChessPiece blackPawnFake;

    private ChessboardPosition from;
    private ChessboardPosition to;
    private ChessboardPosition capturingPos;

    @BeforeEach
    void setUp() {
        moveRecorderStub = new MoveRecorderStub();
        eligibility = new EnPassantEligibility(moveRecorderStub);

        board = new ChessboardFake();

        whitePawnFake = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        blackPawnFake = new PieceFake(ChessPieceColor.BLACK, ChessPieceInfo.PAWN);

        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);
        to = new ChessboardPosition(ChessboardFile.F, ChessboardRank.SIX);
        capturingPos = new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE);
    }

    @Nested
    @DisplayName("when evaluating en passant eligibility")
    class WhenEvaluatingEnPassantEligibility {

        @Test
        @DisplayName("should allow when target is opponent pawn, landing square empty, and last move is valid two-square pawn advance")
        void shouldAllowWhenConditionsAreMet() {
            board.putPieceAt(capturingPos, blackPawnFake);

            ChessboardPosition lastFrom = new ChessboardPosition(ChessboardFile.F, ChessboardRank.SEVEN);
            ChessboardPosition lastTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE);
            moveRecorderStub.setLastMove(new StandardMove(lastFrom, lastTo, blackPawnFake, Optional.empty()));

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, blackPawnFake);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should deny when target is not a pawn")
        void shouldDenyWhenTargetIsNotPawn() {
            ChessPiece nonPawn = new PieceFake(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);
            board.putPieceAt(capturingPos, nonPawn);

            moveRecorderStub.setLastMove(dummyLastMove(nonPawn));

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, nonPawn);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should deny when pawns are same color")
        void shouldDenyWhenPawnsAreSameColor() {
            ChessPiece sameColorPawn = new PieceFake(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);
            board.putPieceAt(capturingPos, sameColorPawn);

            moveRecorderStub.setLastMove(dummyLastMove(sameColorPawn));

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, sameColorPawn);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should deny when landing position is occupied")
        void shouldDenyWhenLandingPositionIsOccupied() {
            board.putPieceAt(capturingPos, blackPawnFake);
            board.putPieceAt(to, new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.KNIGHT));

            moveRecorderStub.setLastMove(validTwoSquarePawnMove(blackPawnFake));

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, blackPawnFake);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should deny when last move is not two-square pawn move")
        void shouldDenyWhenLastMoveIsNotTwoSquarePawnMove() {
            board.putPieceAt(capturingPos, blackPawnFake);

            ChessboardPosition lastFrom = new ChessboardPosition(ChessboardFile.F, ChessboardRank.SIX);
            ChessboardPosition lastTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE);
            moveRecorderStub.setLastMove(new StandardMove(lastFrom, lastTo, blackPawnFake, Optional.empty()));

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, blackPawnFake);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isFalse();
        }
    }

    private static class MoveRecorderStub implements MoveRecorder<ReversibleMove> {
        
        private Optional<ReversibleMove> lastMove = Optional.empty();

        @Override
        public Optional<ReversibleMove> getLastMove() {
            return lastMove;
        }

        public void setLastMove(ReversibleMove move) {
            this.lastMove = Optional.of(move);
        }

        @Override
        public void pushMove(ReversibleMove move) {
            throw new UnsupportedOperationException("pushMove not tested here");
        }

        @Override
        public Optional<ReversibleMove> popMove() {
            throw new UnsupportedOperationException("popMove not tested here");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear not tested here");
        }
    }

    private ReversibleMove validTwoSquarePawnMove(ChessPiece pawn) {
        ChessboardPosition lastFrom = new ChessboardPosition(ChessboardFile.F, ChessboardRank.SEVEN);
        ChessboardPosition lastTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE);
        return new StandardMove(lastFrom, lastTo, pawn, Optional.empty());
    }

    private ReversibleMove dummyLastMove(ChessPiece piece) {
        return new StandardMove(new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO),
                                new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE), piece, Optional.empty());
    }
}