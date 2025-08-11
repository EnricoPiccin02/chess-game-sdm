package test.chessgame.gamelogic.move.standard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.api.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;

import test.chessgame.gamelogic.ChessPieceStub;
import test.chessgame.gamelogic.move.ChessboardSpy;

@DisplayName("StandardMove")
class StandardMoveTest {

    private ChessboardSpy boardSpy;
    private ChessboardPosition from;
    private ChessboardPosition to;
    private ChessPieceStub movedPiece;
    private ChessPieceStub capturedPiece;
    private StandardMove move;

    @BeforeEach
    void setUp() {
        boardSpy = new ChessboardSpy();
        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO);
        to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FOUR);
        movedPiece = new ChessPieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        capturedPiece = new ChessPieceStub(ChessPieceColor.BLACK, ChessPieceInfo.KNIGHT);

        boardSpy.putPieceAt(from, movedPiece);
        boardSpy.putPieceAt(to, capturedPiece);

        move = new StandardMove(from, to, movedPiece, Optional.of(capturedPiece));
    }

    @Test
    @DisplayName("should move the piece and mark it as moved")
    void shouldMovePieceAndMarkAsMoved() {
        move.executeOn(boardSpy);

        assertTrue(movedPiece.hasMoved());
        assertEquals(movedPiece, boardSpy.getPieceAt(to).orElseThrow());
        assertTrue(boardSpy.getPieceAt(from).isEmpty());

        assertTrue(boardSpy.wasPutCalledWith(to, movedPiece));
        assertTrue(boardSpy.wasRemoveCalledWith(from));
    }

    @Test
    @DisplayName("should restore the board to original state after undo")
    void shouldRestoreBoardToOriginalStateWithCapture() {
        move.executeOn(boardSpy);
        move.undoOn(boardSpy);

        assertEquals(movedPiece, boardSpy.getPieceAt(from).orElseThrow());
        assertEquals(capturedPiece, boardSpy.getPieceAt(to).orElseThrow());
        assertTrue(movedPiece.isSnapshotRestored());
    }

    @Test
    @DisplayName("should return starting and landing positions")
    void shouldReturnFromAndTo() {
        List<MoveComponent> components = move.getMoveComponents();
        assertEquals(1, components.size());
        assertEquals(from, components.get(0).from());
        assertEquals(to, components.get(0).to());
    }
}