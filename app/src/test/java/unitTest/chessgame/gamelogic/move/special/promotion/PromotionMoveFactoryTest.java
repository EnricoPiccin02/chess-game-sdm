package unittest.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;
import unittest.chessgame.gamelogic.testdoubles.PieceFake;

@DisplayName("PromotionMoveFactory")
class PromotionMoveFactoryTest {

    private PromotionMoveFactory factory;
    private PromotionPieceSelectorStub selectorStub;

    private ChessPiece pawnFake;
    private ChessPiece promotedPieceDummy;
    private ChessPiece capturedPieceDummy;

    private ChessboardPosition from;
    private ChessboardPosition forwardTo;
    private ChessboardPosition capturingTo;

    @BeforeEach
    void setUp() {
        pawnFake = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        promotedPieceDummy = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.QUEEN);
        capturedPieceDummy = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);

        selectorStub = new PromotionPieceSelectorStub(promotedPieceDummy);
        factory = new PromotionMoveFactory(selectorStub);

        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
        forwardTo = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
        capturingTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT);
    }

    @Nested
    @DisplayName("when promoting without capture")
    class WhenPromotingWithoutCapture {

        @Test
        @DisplayName("should replace pawn with promoted piece on promotion rank")
        void shouldReplacePawnWithPromotedPiece() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnFake, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            ChessboardFake board = new ChessboardFake();
            board.putPieceAt(from, pawnFake);

            move.executeOn(board);

            assertThat(board.getPieceAt(forwardTo)).contains(promotedPieceDummy);
        }

        @Test
        @DisplayName("should clear pawn original square after promotion")
        void shouldClearPawnOriginalSquare() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnFake, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            ChessboardFake board = new ChessboardFake();
            board.putPieceAt(from, pawnFake);

            move.executeOn(board);

            assertThat(board.getPieceAt(from)).isEmpty();
        }

        @Test
        @DisplayName("should restore pawn on undo")
        void shouldRestorePawnOnUndo() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnFake, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            ChessboardFake board = new ChessboardFake();
            board.putPieceAt(from, pawnFake);

            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.getPieceAt(from)).contains(pawnFake);
        }

        @Test
        @DisplayName("should clear promoted square on undo")
        void shouldClearPromotedSquareOnUndo() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnFake, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            ChessboardFake board = new ChessboardFake();
            board.putPieceAt(from, pawnFake);

            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.getPieceAt(forwardTo)).isEmpty();
        }

        @Test
        @DisplayName("should choose promotion piece based on pawn color")
        void shouldChoosePromotionPieceBasedOnPawnColor() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnFake, Optional.empty());
            factory.create(candidate).executeOn(new ChessboardFake());

            assertThat(selectorStub.selectedColor).isEqualTo(ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should expose pawn forward move component")
        void shouldExposePawnForwardMoveComponent() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnFake, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            MoveComponent primary = move.getPrimaryMoveComponent();

            assertThat(primary.from()).isEqualTo(from);
            assertThat(primary.to()).isEqualTo(forwardTo);
        }
    }

    @Nested
    @DisplayName("when promoting with capture")
    class WhenPromotingWithCapture {

        @Test
        @DisplayName("should replace captured piece with promoted piece")
        void shouldReplaceCapturedPieceWithPromotedPiece() {
            PromotionCandidate candidate = new PromotionCandidate(from, capturingTo, pawnFake, Optional.of(capturedPieceDummy));
            ReversibleMove move = factory.create(candidate);

            ChessboardFake board = new ChessboardFake();
            board.putPieceAt(from, pawnFake);
            board.putPieceAt(capturingTo, capturedPieceDummy);

            move.executeOn(board);

            assertThat(board.getPieceAt(capturingTo)).contains(promotedPieceDummy);
        }

        @Test
        @DisplayName("should clear pawn original square after capture promotion")
        void shouldClearPawnOriginalSquareAfterCapturePromotion() {
            PromotionCandidate candidate = new PromotionCandidate(from, capturingTo, pawnFake, Optional.of(capturedPieceDummy));
            ReversibleMove move = factory.create(candidate);

            ChessboardFake board = new ChessboardFake();
            board.putPieceAt(from, pawnFake);
            board.putPieceAt(capturingTo, capturedPieceDummy);

            move.executeOn(board);

            assertThat(board.getPieceAt(from)).isEmpty();
        }

        @Test
        @DisplayName("should restore pawn on undo after capture promotion")
        void shouldRestorePawnOnUndoAfterCapturePromotion() {
            PromotionCandidate candidate = new PromotionCandidate(from, capturingTo, pawnFake, Optional.of(capturedPieceDummy));
            ReversibleMove move = factory.create(candidate);

            ChessboardFake board = new ChessboardFake();
            board.putPieceAt(from, pawnFake);
            board.putPieceAt(capturingTo, capturedPieceDummy);

            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.getPieceAt(from)).contains(pawnFake);
        }

        @Test
        @DisplayName("should restore captured piece on undo after capture promotion")
        void shouldRestoreCapturedPieceOnUndoAfterCapturePromotion() {
            PromotionCandidate candidate = new PromotionCandidate(from, capturingTo, pawnFake, Optional.of(capturedPieceDummy));
            ReversibleMove move = factory.create(candidate);

            ChessboardFake board = new ChessboardFake();
            board.putPieceAt(from, pawnFake);
            board.putPieceAt(capturingTo, capturedPieceDummy);

            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.getPieceAt(capturingTo)).contains(capturedPieceDummy);
        }

        @Test
        @DisplayName("should expose pawn capturing move component")
        void shouldExposePawnCapturingMoveComponent() {
            PromotionCandidate candidate = new PromotionCandidate(from, capturingTo, pawnFake, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            MoveComponent primary = move.getPrimaryMoveComponent();

            assertThat(primary.from()).isEqualTo(from);
            assertThat(primary.to()).isEqualTo(capturingTo);
        }
    }

    private static class PromotionPieceSelectorStub implements PromotionPieceSelector {

        private final ChessPiece pieceToReturn;
        private ChessPieceColor selectedColor;

        PromotionPieceSelectorStub(ChessPiece pieceToReturn) {
            this.pieceToReturn = pieceToReturn;
        }

        @Override
        public ChessPiece selectPromotionPiece(ChessPieceColor color) {
            this.selectedColor = color;
            return pieceToReturn;
        }
    }
}