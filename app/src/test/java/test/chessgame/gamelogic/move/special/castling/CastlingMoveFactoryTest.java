package test.chessgame.gamelogic.move.special.castling;

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

import test.chessgame.gamelogic.testdoubles.ChessboardSpy;
import test.chessgame.gamelogic.testdoubles.PieceFake;

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
    }

    @Nested
    @DisplayName("when creating a castling move")
    class WhenCreatingCastlingMove {

        @Test
        @DisplayName("should create a CastlingMove that moves both king and rook")
        void shouldCreateCastlingMoveThatMovesBothKingAndRook() {
            CastlingCandidate candidate = new CastlingCandidate(
                kingFrom, kingTo,
                rookFrom, rookTo,
                king, rook
            );

            ReversibleMove move = factory.create(candidate);
            CaptureResult result = move.executeOn(board);

            assertThat(result.isCapture()).isFalse();
            assertThat(board.wasRemoveCalledWith(kingFrom)).isTrue();
            assertThat(board.wasPutCalledWith(kingTo, king)).isTrue();
            assertThat(board.wasRemoveCalledWith(rookFrom)).isTrue();
            assertThat(board.wasPutCalledWith(rookTo, rook)).isTrue();
        }

        @Test
        @DisplayName("should undo castling by restoring both king and rook to original positions")
        void shouldUndoCastlingByRestoringBothKingAndRook() {
            CastlingCandidate candidate = new CastlingCandidate(
                kingFrom, kingTo,
                rookFrom, rookTo,
                king, rook
            );
            ReversibleMove move = factory.create(candidate);
            move.executeOn(board);

            CaptureResult result = move.undoOn(board);

            assertThat(result.isCapture()).isFalse();
            assertThat(board.wasRemoveCalledWith(kingTo)).isTrue();
            assertThat(board.wasPutCalledWith(kingFrom, king)).isTrue();
            assertThat(board.wasRemoveCalledWith(rookTo)).isTrue();
            assertThat(board.wasPutCalledWith(rookFrom, rook)).isTrue();
        }

        @Test
        @DisplayName("should return king's primary move component")
        void shouldReturnKingsPrimaryMoveComponent() {
            CastlingCandidate candidate = new CastlingCandidate(
                kingFrom, kingTo,
                rookFrom, rookTo,
                king, rook
            );
            ReversibleMove move = factory.create(candidate);

            MoveComponent primary = move.getPrimaryMoveComponent();

            assertThat(primary.from()).isEqualTo(kingFrom);
            assertThat(primary.to()).isEqualTo(kingTo);
        }
    }
}