package unittest.chessgame.gamelogic.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.pieces.Bishop;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

@DisplayName("ChessPiece")
class ChessPieceTest {

    private ChessPiece whitePiece;
    private ChessPiece blackPiece;

    @BeforeEach
    void setUp() {
        whitePiece = new Pawn(ChessPieceColor.WHITE, ChessboardOrientation.WHITE_BOTTOM);
        blackPiece = new Rook(ChessPieceColor.BLACK);
    }

    @Nested
    @DisplayName("hasMoved() and markAsMoved()")
    class HasMoved {

        @Test
        @DisplayName("should be false initially")
        void shouldBeFalseInitially() {
            assertThat(whitePiece.hasMoved()).isFalse();
        }

        @Test
        @DisplayName("should be true after markAsMoved")
        void shouldBeTrueAfterMarkAsMoved() {
            whitePiece.markAsMoved();
            assertThat(whitePiece.hasMoved()).isTrue();
        }
    }

    @Nested
    @DisplayName("isOpponentOf(color)")
    class IsOpponentOf {

        @Test
        @DisplayName("should return true when colors differ")
        void shouldReturnTrueWhenColorsDiffer() {
            assertThat(whitePiece.isOpponentOf(ChessPieceColor.BLACK)).isTrue();
        }

        @Test
        @DisplayName("should return false when colors match")
        void shouldReturnFalseWhenColorsMatch() {
            assertThat(blackPiece.isOpponentOf(ChessPieceColor.BLACK)).isFalse();
        }
    }

    @Nested
    @DisplayName("snapshot")
    class Snapshot {

        @Test
        @DisplayName("should preserve movement state")
        void shouldPreserveMovementState() {
            whitePiece.markAsMoved();
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();

            whitePiece.restoreSnapshot(snapshot);

            assertThat(snapshot.getPiece()).isSameAs(whitePiece);
            assertThat(whitePiece.hasMoved()).isTrue();
            assertThat(snapshot.wasMoved()).isTrue();
        }

        @Test
        @DisplayName("should restore original moved state on same instance")
        void shouldRestoreMovedStateOnSameInstance() {
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();
            
            whitePiece.markAsMoved();

            assertThat(whitePiece.hasMoved()).isTrue();

            whitePiece.restoreSnapshot(snapshot);
            assertThat(whitePiece.hasMoved()).isFalse();
        }

        @Test
        @DisplayName("should not affect different instance with different identity")
        void shouldNotAffectDifferentInstance() {
            whitePiece.markAsMoved();
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();

            ChessPiece another = new Bishop(ChessPieceColor.WHITE);
            another.restoreSnapshot(snapshot);

            assertThat(another.hasMoved()).isFalse();
            assertThat(whitePiece.hasMoved()).isTrue();
        }
    }
}