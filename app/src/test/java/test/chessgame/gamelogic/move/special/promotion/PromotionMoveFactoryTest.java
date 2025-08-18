package test.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

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
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import test.chessgame.gamelogic.testdoubles.ChessboardFake;
import test.chessgame.gamelogic.testdoubles.ChessboardSpy;
import test.chessgame.gamelogic.testdoubles.PieceDummy;
import test.chessgame.gamelogic.testdoubles.PieceStub;

@DisplayName("PromotionMoveFactory")
class PromotionMoveFactoryTest {

    private PromotionMoveFactory factory;
    private PromotionPieceSelectorStub selectorStub;

    private ChessPiece pawnStub;
    private ChessPiece promotedPieceDummy;
    private ChessPiece capturedPieceDummy;

    private ChessboardPosition from;
    private ChessboardPosition forwardTo;
    private ChessboardPosition capturingTo;

    @BeforeEach
    void setUp() {
        pawnStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, Set.of());
        promotedPieceDummy = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.QUEEN);
        capturedPieceDummy = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);

        selectorStub = new PromotionPieceSelectorStub(promotedPieceDummy);
        factory = new PromotionMoveFactory(selectorStub);

        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
        forwardTo = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
        capturingTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT);
    }

    @Nested
    @DisplayName("when creating a normal promotion move")
    class WhenCreatingNormalPromotionMove {

        @Test
        @DisplayName("should move pawn to promotion rank and replace with chosen piece")
        void shouldPromotePawn() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnStub, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, pawnStub);

            move.executeOn(board);

            assertThat(board.getPieceAt(forwardTo)).contains(promotedPieceDummy);
            assertThat(board.getPieceAt(from)).isEmpty();

            assertThat(board.wasRemoveCalledWith(from)).isTrue();
            assertThat(board.wasPutCalledWith(forwardTo, pawnStub)).isTrue();
            assertThat(board.wasPutCalledWith(forwardTo, promotedPieceDummy)).isTrue();
        }

        @Test
        @DisplayName("should undo promotion and restore pawn to original position")
        void shouldUndoPromotion() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnStub, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, pawnStub);

            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.getPieceAt(from)).contains(pawnStub);
            assertThat(board.getPieceAt(forwardTo)).isEmpty();

            assertThat(board.wasPutCalledWith(from, pawnStub)).isTrue();
            assertThat(board.wasRemoveCalledWith(forwardTo)).isTrue();
        }

        @Test
        @DisplayName("should select promotion piece based on pawn color")
        void shouldSelectPromotionPieceBasedOnPawnColor() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnStub, Optional.empty());
            factory.create(candidate).executeOn(new ChessboardFake());

            assertThat(selectorStub.selectedColor).isEqualTo(ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should return pawn forward move component")
        void shouldReturnPawnForwardMoveComponent() {
            PromotionCandidate candidate = new PromotionCandidate(from, forwardTo, pawnStub, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            MoveComponent primary = move.getPrimaryMoveComponent();

            assertThat(primary.from()).isEqualTo(from);
            assertThat(primary.to()).isEqualTo(forwardTo);
        }
    }

    @Nested
    @DisplayName("when creating a capture promotion move")
    class WhenCreatingCapturePromotionMove {

        @Test
        @DisplayName("should promote pawn and capture enemy piece")
        void shouldPromoteAndCapture() {
            PromotionCandidate candidate = new PromotionCandidate(from, capturingTo, pawnStub, Optional.of(capturedPieceDummy));
            ReversibleMove move = factory.create(candidate);

            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, pawnStub);
            board.putPieceAt(capturingTo, capturedPieceDummy);

            CaptureResult result = move.executeOn(board);

            assertThat(board.getPieceAt(capturingTo)).contains(promotedPieceDummy);
            assertThat(board.getPieceAt(from)).isEmpty();
            assertTrue(result.pieceValue().isPresent());
            assertEquals(capturedPieceDummy.pieceInfo().getPieceValue(), result.pieceValue().getAsInt());

            assertThat(board.wasRemoveCalledWith(from)).isTrue();
            assertThat(board.wasPutCalledWith(capturingTo, pawnStub)).isTrue();
            assertThat(board.wasPutCalledWith(capturingTo, promotedPieceDummy)).isTrue();
        }

        @Test
        @DisplayName("should undo capture promotion and restore both pieces")
        void shouldUndoCapturePromotion() {
            PromotionCandidate candidate = new PromotionCandidate(from, capturingTo, pawnStub, Optional.of(capturedPieceDummy));
            ReversibleMove move = factory.create(candidate);

            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, pawnStub);
            board.putPieceAt(capturingTo, capturedPieceDummy);

            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.getPieceAt(from)).contains(pawnStub);
            assertThat(board.getPieceAt(capturingTo)).contains(capturedPieceDummy);

            assertThat(board.wasPutCalledWith(from, pawnStub)).isTrue();
            assertThat(board.wasRemoveCalledWith(capturingTo)).isTrue();
        }

        @Test
        @DisplayName("should return pawn capturing move component")
        void shouldReturnPawnCapturingMoveComponent() {
            PromotionCandidate candidate = new PromotionCandidate(from, capturingTo, pawnStub, Optional.empty());
            ReversibleMove move = factory.create(candidate);

            MoveComponent primary = move.getPrimaryMoveComponent();

            assertThat(primary.from()).isEqualTo(from);
            assertThat(primary.to()).isEqualTo(capturingTo);
        }
    }

    private static class PromotionPieceSelectorStub implements PromotionPieceSelector {

        private final ChessPiece pieceToReturn;
        private ChessPieceColor selectedColor;

        public PromotionPieceSelectorStub(ChessPiece pieceToReturn) {
            this.pieceToReturn = pieceToReturn;
        }

        @Override
        public ChessPiece selectPromotionPiece(ChessPieceColor color) {
            this.selectedColor = color;
            return pieceToReturn;
        }
    }
}