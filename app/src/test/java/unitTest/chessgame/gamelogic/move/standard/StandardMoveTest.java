package unitTest.chessgame.gamelogic.move.standard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unitTest.chessgame.gamelogic.testdoubles.ChessboardSpy;
import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;
import unitTest.chessgame.gamelogic.testdoubles.PieceFake;

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

    @Test
    @DisplayName("should move the piece and mark it as moved")
    void shouldMovePieceAndMarkAsMoved() {
        move.executeOn(boardSpy);

        assertTrue(movingPieceFake.hasMoved());
        assertEquals(movingPieceFake, boardSpy.getPieceAt(to).orElseThrow());
        assertTrue(boardSpy.getPieceAt(from).isEmpty());

        assertTrue(boardSpy.wasPutCalledWith(to, movingPieceFake));
        assertTrue(boardSpy.wasRemoveCalledWith(from));
    }

    @Test
    @DisplayName("should capture provided piece")
    void shouldCaptureProvidedPiece() {
        CaptureResult result = move.executeOn(boardSpy);

        assertTrue(result.pieceValue().isPresent());
        assertEquals(capturedPieceDummy.pieceInfo().getPieceValue(), result.pieceValue().getAsInt());
    }

    @Test
    @DisplayName("should restore the board to original state after undo")
    void shouldRestoreBoardToOriginalStateWithCapture() {
        move.executeOn(boardSpy);
        move.undoOn(boardSpy);

        assertEquals(movingPieceFake, boardSpy.getPieceAt(from).orElseThrow());
        assertEquals(capturedPieceDummy, boardSpy.getPieceAt(to).orElseThrow());

        assertTrue(boardSpy.wasPutCalledWith(to, capturedPieceDummy));
        assertTrue(boardSpy.wasPutCalledWith(from, movingPieceFake));
        assertTrue(boardSpy.wasRemoveCalledWith(from));
    }

    @Test
    @DisplayName("should return starting and landing positions")
    void shouldReturnFromAndTo() {
        MoveComponent components = move.getPrimaryMoveComponent();
        assertEquals(from, components.from());
        assertEquals(to, components.to());
        assertEquals(from, move.from());
        assertEquals(to, move.to());
    }
}