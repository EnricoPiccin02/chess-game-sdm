package unittest.chessgame.gamelogic.move.standard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.ChessboardSpy;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;
import unittest.chessgame.gamelogic.testdoubles.PieceFake;

@DisplayName("StandardMove")
class StandardMoveTest {

    private ChessboardSpy boardSpy;
    private ChessboardPosition from;
    private ChessboardPosition to;
    private PieceFake movingPieceFake;
    private ChessPiece capturedPieceDummy;
    private StandardMove move;

    @BeforeEach
    void setUp() {
        boardSpy = new ChessboardSpy();
        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO);
        to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FOUR);
        movingPieceFake = new PieceFake(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        capturedPieceDummy = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.KNIGHT);

        boardSpy.putPieceAt(from, movingPieceFake);
        boardSpy.putPieceAt(to, capturedPieceDummy);

        move = new StandardMove(from, to, movingPieceFake, Optional.of(capturedPieceDummy));
    }

    @Nested
    @DisplayName("when executed")
    class WhenExecuted {

        @Test
        @DisplayName("should move the piece to target square")
        void shouldMovePieceToTargetSquare() {
            move.executeOn(boardSpy);

            assertEquals(movingPieceFake, boardSpy.getPieceAt(to).orElseThrow());
        }

        @Test
        @DisplayName("should remove the piece from original square")
        void shouldRemovePieceFromOriginalSquare() {
            move.executeOn(boardSpy);

            assertTrue(boardSpy.getPieceAt(from).isEmpty());
        }

        @Test
        @DisplayName("should mark the piece as moved")
        void shouldMarkPieceAsMoved() {
            move.executeOn(boardSpy);

            assertTrue(movingPieceFake.hasMoved());
        }

        @Test
        @DisplayName("should capture the opponent piece")
        void shouldCaptureOpponentPiece() {
            CaptureResult result = move.executeOn(boardSpy);

            assertEquals(
                capturedPieceDummy.pieceInfo().getPieceValue(),
                result.pieceValue().orElseThrow()
            );
        }

        @Test
        @DisplayName("should delegate the board about piece placement")
        void shouldDelegateBoardAboutPlacement() {
            move.executeOn(boardSpy);

            assertTrue(boardSpy.wasPutCalledWith(to, movingPieceFake));
        }

        @Test
        @DisplayName("should delegate the board about piece removal")
        void shouldDelegateBoardAboutRemoval() {
            move.executeOn(boardSpy);

            assertTrue(boardSpy.wasRemoveCalledWith(from));
        }
    }

    @Nested
    @DisplayName("when undone")
    class WhenUndone {

        @BeforeEach
        void executeMove() {
            move.executeOn(boardSpy);
        }

        @Test
        @DisplayName("should restore the moving piece to original square")
        void shouldRestoreMovingPiece() {
            move.undoOn(boardSpy);

            assertEquals(movingPieceFake, boardSpy.getPieceAt(from).orElseThrow());
        }

        @Test
        @DisplayName("should restore the captured piece to target square")
        void shouldRestoreCapturedPiece() {
            move.undoOn(boardSpy);

            assertEquals(capturedPieceDummy, boardSpy.getPieceAt(to).orElseThrow());
        }

        @Test
        @DisplayName("should delegate the board about restoring captured piece")
        void shouldDelegateBoardAboutRestoringCapturedPiece() {
            move.undoOn(boardSpy);

            assertTrue(boardSpy.wasPutCalledWith(to, capturedPieceDummy));
        }

        @Test
        @DisplayName("should delegate the board about restoring moving piece")
        void shouldDelegateBoardAboutRestoringMovingPiece() {
            move.undoOn(boardSpy);

            assertTrue(boardSpy.wasPutCalledWith(from, movingPieceFake));
        }
    }

    @Nested
    @DisplayName("when queried for positions")
    class WhenQueriedForPositions {

        @Test
        @DisplayName("should provide origin square")
        void shouldProvideOriginSquare() {
            assertEquals(from, move.from());
        }

        @Test
        @DisplayName("should provide destination square")
        void shouldProvideDestinationSquare() {
            assertEquals(to, move.to());
        }

        @Test
        @DisplayName("should expose origin through move component")
        void shouldExposeOriginThroughComponent() {
            assertEquals(from, move.getPrimaryMoveComponent().from());
        }

        @Test
        @DisplayName("should expose destination through move component")
        void shouldExposeDestinationThroughComponent() {
            assertEquals(to, move.getPrimaryMoveComponent().to());
        }
    }
}