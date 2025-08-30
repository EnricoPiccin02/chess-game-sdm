package unittest.chessgame.gamelogic.move.special.enpassant;

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

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;
import unittest.chessgame.gamelogic.testdoubles.PieceFake;

@DisplayName("EnPassantEligibility")
class EnPassantEligibilityTest {

    private EnPassantEligibility eligibility;
    private MoveRecorderStub moveRecorderStub;
    private ChessboardFake board;

    private ChessPiece whitePawnFake;
    private ChessPiece blackPawnFake;

    private ChessboardPosition from;
    private ChessboardPosition to;
    private ChessboardPosition fromCapture;
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

        fromCapture = new ChessboardPosition(ChessboardFile.F, ChessboardRank.SEVEN);
        capturingPos = new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE);
    }

    @Nested
    @DisplayName("when all conditions are met")
    class WhenAllConditionsMet {

        @Test
        @DisplayName("should recognize valid and eligible en passant")
        void shouldRecognizeValidEnPassant() {
            board.putPieceAt(capturingPos, blackPawnFake);

            moveRecorderStub.setLastMove(fromCapture, capturingPos, blackPawnFake);

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, blackPawnFake);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("when any condition fails")
    class WhenAnyConditionFails {

        @Test
        @DisplayName("should deny when target is not a pawn")
        void shouldDenyWhenTargetIsNotPawn() {
            ChessPiece nonPawn = new PieceFake(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);
            board.putPieceAt(capturingPos, nonPawn);

            moveRecorderStub.setDummyLastMove(nonPawn);

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, nonPawn);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should deny when pawns are same color")
        void shouldDenyWhenPawnsAreSameColor() {
            ChessPiece sameColorPawn = new PieceFake(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);
            board.putPieceAt(capturingPos, sameColorPawn);

            moveRecorderStub.setDummyLastMove(sameColorPawn);

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, sameColorPawn);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should deny when landing position is occupied")
        void shouldDenyWhenLandingPositionIsOccupied() {
            board.putPieceAt(capturingPos, blackPawnFake);
            board.putPieceAt(to, new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.KNIGHT));

            moveRecorderStub.setLastMove(fromCapture, capturingPos, blackPawnFake);

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, blackPawnFake);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should deny when last move is not two-square pawn move")
        void shouldDenyWhenLastMoveIsNotTwoSquarePawnMove() {
            board.putPieceAt(capturingPos, blackPawnFake);

            moveRecorderStub.setDummyLastMove(blackPawnFake);

            EnPassantCandidate candidate = new EnPassantCandidate(from, to, capturingPos, whitePawnFake, blackPawnFake);

            boolean result = eligibility.canExecute(board, candidate, ChessboardOrientation.WHITE_BOTTOM);

            assertThat(result).isFalse();
        }
    }
    
    private static class MoveRecorderStub implements MoveRecorder<ReversibleMove> {
        
        private ReversibleMove lastMove;

        @Override
        public Optional<ReversibleMove> getLastMove() {
            return Optional.of(lastMove);
        }

        public void setLastMove(ChessboardPosition from, ChessboardPosition to, ChessPiece piece) {
            this.lastMove = new StandardMove(from, to, piece, Optional.empty());
        }

        public void setDummyLastMove(ChessPiece dummyPiece) {
            this.lastMove = new StandardMove(
                new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO),
                new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE),
                dummyPiece,
                Optional.empty()
            );
        }

        @Override
        public void pushMove(ReversibleMove move) {}

        @Override
        public Optional<ReversibleMove> popMove() {
            return Optional.empty();
        }

        @Override
        public void clear() {}
    }
}