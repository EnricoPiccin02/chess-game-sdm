package unittest.chessgame.gamelogic.move.special.castling;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingMoveFactory;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.ChessboardSpy;
import unittest.chessgame.gamelogic.testdoubles.PieceFake;

@DisplayName("CastlingMoveFactory")
class CastlingMoveFactoryTest {

    private CastlingMoveFactory factory;
    private ChessboardSpy board;
    private ChessPiece king;
    private ChessPiece rook;
    private ChessboardPosition kingFrom;
    private ChessboardPosition kingTo;
    private ChessboardPosition rookFrom;
    private ChessboardPosition rookTo;
    private CastlingCandidate candidate;
    private ReversibleMove move;

    @BeforeEach
    void setUp() {
        factory = new CastlingMoveFactory();
        board = new ChessboardSpy();

        kingFrom = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
        kingTo = new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE);
        rookFrom = new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE);
        rookTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE);

        king = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.KING);
        rook = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);

        board.putPieceAt(kingFrom, king);
        board.putPieceAt(rookFrom, rook);

        candidate = new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo, king, rook);
        move = factory.create(candidate);
    }

    @Nested
    @DisplayName("when executing a castling move")
    class ExecuteCastlingMove {

        @Test
        @DisplayName("should not result in a capture")
        void shouldNotResultInCapture() {
            CaptureResult result = move.executeOn(board);
            assertThat(result.isCapture()).isFalse();
        }

        @Test
        @DisplayName("should move the king to target square")
        void shouldMoveKingToTargetSquare() {
            move.executeOn(board);
            assertThat(board.wasPutCalledWith(kingTo, king)).isTrue();
        }

        @Test
        @DisplayName("should remove the king from original square")
        void shouldRemoveKingFromOriginalSquare() {
            move.executeOn(board);
            assertThat(board.wasRemoveCalledWith(kingFrom)).isTrue();
        }

        @Test
        @DisplayName("should move the rook to target square")
        void shouldMoveRookToTargetSquare() {
            move.executeOn(board);
            assertThat(board.wasPutCalledWith(rookTo, rook)).isTrue();
        }

        @Test
        @DisplayName("should remove the rook from original square")
        void shouldRemoveRookFromOriginalSquare() {
            move.executeOn(board);
            assertThat(board.wasRemoveCalledWith(rookFrom)).isTrue();
        }
    }

    @Nested
    @DisplayName("when undoing a castling move")
    class UndoCastlingMove {

        @BeforeEach
        void executeBeforeUndo() {
            move.executeOn(board);
        }

        @Test
        @DisplayName("should not result in a capture")
        void shouldNotResultInCapture() {
            CaptureResult result = move.undoOn(board);
            assertThat(result.isCapture()).isFalse();
        }

        @Test
        @DisplayName("should restore the king to original square")
        void shouldRestoreKingToOriginalSquare() {
            move.undoOn(board);
            assertThat(board.wasPutCalledWith(kingFrom, king)).isTrue();
        }

        @Test
        @DisplayName("should remove the king from target square")
        void shouldRemoveKingFromTargetSquare() {
            move.undoOn(board);
            assertThat(board.wasRemoveCalledWith(kingTo)).isTrue();
        }

        @Test
        @DisplayName("should restore the rook to original square")
        void shouldRestoreRookToOriginalSquare() {
            move.undoOn(board);
            assertThat(board.wasPutCalledWith(rookFrom, rook)).isTrue();
        }

        @Test
        @DisplayName("should remove the rook from target square")
        void shouldRemoveRookFromTargetSquare() {
            move.undoOn(board);
            assertThat(board.wasRemoveCalledWith(rookTo)).isTrue();
        }
    }

    @Nested
    @DisplayName("when inspecting castling move components")
    class InspectMoveComponents {

        @Test
        @DisplayName("should expose king's primary move component")
        void shouldProvideKingsPrimaryMoveComponent() {
            MoveComponent primary = move.getPrimaryMoveComponent();
            assertThat(primary.from()).isEqualTo(kingFrom);
            assertThat(primary.to()).isEqualTo(kingTo);
        }
    }
}