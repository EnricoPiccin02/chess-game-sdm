package unitTest.chessgame.gamelogic.move.special.enpassant;

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
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantMove;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantMoveFactory;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unitTest.chessgame.gamelogic.testdoubles.ChessboardSpy;
import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;
import unitTest.chessgame.gamelogic.testdoubles.PieceFake;

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
    @DisplayName("when creating an en passant move")
    class WhenCreatingEnPassantMove {

        @Test
        @DisplayName("should remove adjacent pawn and move diagonally to target square")
        void shouldRemoveAdjacentPawnAndMoveDiagonally() {
            ReversibleMove move = factory.create(candidate);

            assertThat(move).isInstanceOf(EnPassantMove.class);

            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, movingPawnFake);
            board.putPieceAt(capturingPosition, capturedPawnDummy);

            move.executeOn(board);

            assertThat(board.wasRemoveCalledWith(from)).isTrue();
            assertThat(board.wasRemoveCalledWith(capturingPosition)).isTrue();
            assertThat(board.wasPutCalledWith(to, movingPawnFake)).isTrue();
        }

        @Test
        @DisplayName("should restore captured pawn and original pawn position on undo")
        void shouldRestoreCapturedPawnAndOriginalPawnPositionOnUndo() {
            ReversibleMove move = factory.create(candidate);

            ChessboardSpy board = new ChessboardSpy();
            board.putPieceAt(from, movingPawnFake);
            board.putPieceAt(capturingPosition, capturedPawnDummy);

            move.executeOn(board);
            move.undoOn(board);

            assertThat(board.wasPutCalledWith(from, movingPawnFake)).isTrue();
            assertThat(board.wasPutCalledWith(capturingPosition, capturedPawnDummy)).isTrue();
            assertThat(board.wasRemoveCalledWith(to)).isTrue();
        }

        @Test
        @DisplayName("should return pawn diagonal move component")
        void shouldReturnPawnDiagonalMoveComponent() {
            ReversibleMove move = factory.create(candidate);

            MoveComponent primary = move.getPrimaryMoveComponent();

            assertThat(primary.from()).isEqualTo(from);
            assertThat(primary.to()).isEqualTo(to);
        }
    }
}