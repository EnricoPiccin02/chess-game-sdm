package unittest.chessgame.gamelogic.move.special.enpassant;

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
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantMoveFactory;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.ChessboardSpy;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;
import unittest.chessgame.gamelogic.testdoubles.PieceFake;

@DisplayName("EnPassantMoveFactory")
class EnPassantMoveFactoryTest {

    private EnPassantMoveFactory factory;

    private ChessPiece movingPawnFake;
    private ChessPiece capturedPawnDummy;

    private ChessboardPosition from;
    private ChessboardPosition to;
    private ChessboardPosition capturingPosition;

    private EnPassantCandidate candidate;

    @BeforeEach
    void setUp() {
        movingPawnFake = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        capturedPawnDummy = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.PAWN);

        factory = new EnPassantMoveFactory();

        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);
        capturingPosition = new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE);
        to = new ChessboardPosition(ChessboardFile.F, ChessboardRank.SIX);

        candidate = new EnPassantCandidate(
            from, to, capturingPosition, movingPawnFake, capturedPawnDummy
        );
    }

    @Nested
    @DisplayName("when executing an en passant move")
    class ExecuteEnPassantMove {

        @Test
        @DisplayName("should move pawn diagonally to target square")
        void shouldMovePawnDiagonallyToTargetSquare() {
            ReversibleMove move = factory.create(candidate);
            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, movingPawnFake);

            move.executeOn(board);

            assertThat(board.wasPutCalledWith(to, movingPawnFake)).isTrue();
        }

        @Test
        @DisplayName("should remove adjacent pawn when capturing en passant")
        void shouldRemoveAdjacentPawnWhenCapturing() {
            ReversibleMove move = factory.create(candidate);
            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(capturingPosition, capturedPawnDummy);

            move.executeOn(board);

            assertThat(board.wasRemoveCalledWith(capturingPosition)).isTrue();
        }

        @Test
        @DisplayName("should clear original pawn square after moving")
        void shouldClearOriginalPawnSquareAfterMoving() {
            ReversibleMove move = factory.create(candidate);
            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, movingPawnFake);

            move.executeOn(board);

            assertThat(board.wasRemoveCalledWith(from)).isTrue();
        }
    }

    @Nested
    @DisplayName("when undoing an en passant move")
    class UndoEnPassantMove {

        @Test
        @DisplayName("should restore pawn to original position when undone")
        void shouldRestorePawnToOriginalPositionWhenUndone() {
            ReversibleMove move = factory.create(candidate);
            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, movingPawnFake);
            
            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.wasPutCalledWith(from, movingPawnFake)).isTrue();
        }

        @Test
        @DisplayName("should restore captured pawn when undone")
        void shouldRestoreCapturedPawnWhenUndone() {
            ReversibleMove move = factory.create(candidate);
            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(capturingPosition, capturedPawnDummy);
            
            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.wasPutCalledWith(capturingPosition, capturedPawnDummy)).isTrue();
        }

        @Test
        @DisplayName("should clear target square when undone")
        void shouldClearTargetSquareWhenUndone() {
            ReversibleMove move = factory.create(candidate);
            ChessboardSpy board = new ChessboardSpy();
            
            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.wasRemoveCalledWith(to)).isTrue();
        }
    }

    @Nested
    @DisplayName("when inspecting en passant move components")
    class InspectMoveComponents {

        @Test
        @DisplayName("should expose pawn diagonal move component")
        void shouldProvidePawnDiagonalMoveComponent() {
            ReversibleMove move = factory.create(candidate);

            MoveComponent primary = move.getPrimaryMoveComponent();

            assertThat(primary.from()).isEqualTo(from);
            assertThat(primary.to()).isEqualTo(to);
        }
    }
}