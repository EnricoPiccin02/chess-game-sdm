package test.chessgame.gamelogic.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.pieces.Bishop;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;
import com.sdm.units.chessgame.gamelogic.pieces.King;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

import test.chessgame.gamelogic.ChessPieceStub;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DisplayName("ChessPiece")
class ChessPieceTest {

    private ChessPiece whitePiece;
    private ChessPiece blackPiece;

    @BeforeEach
    void setUp() {
        whitePiece = new ChessPieceStub(ChessPieceColor.WHITE, ChessPieceInfo.QUEEN);
        blackPiece = new ChessPieceStub(ChessPieceColor.BLACK, ChessPieceInfo.ROOK);
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

            // Restoring on the *same* piece should give back identical state
            whitePiece.restoreSnapshot(snapshot);

            assertThat(snapshot.getPiece()).isSameAs(whitePiece);
            assertThat(whitePiece.hasMoved()).isTrue();
            assertThat(snapshot.wasMoved()).isTrue();
        }

        @Test
        @DisplayName("should restore original moved state on same instance")
        void shouldRestoreMovedStateOnSameInstance() {
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();
            
            // Mutate after snapshot
            whitePiece.markAsMoved();

            assertThat(whitePiece.hasMoved()).isTrue();

            // Now restore
            whitePiece.restoreSnapshot(snapshot);
            assertThat(whitePiece.hasMoved()).isFalse();
        }

        @Test
        @DisplayName("should not affect different instance with different identity")
        void shouldNotAffectDifferentInstance() {
            whitePiece.markAsMoved();
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();

            ChessPiece another = new ChessPieceStub(ChessPieceColor.WHITE, ChessPieceInfo.BISHOP);
            another.restoreSnapshot(snapshot);

            // No change expected because snapshot belongs to different instance
            assertThat(another.hasMoved()).isFalse();
            assertThat(whitePiece.hasMoved()).isTrue();
        }
    }

    @Nested
    @DisplayName("copy")
    class Copy {

        @Test
        @DisplayName("should perform a deep copy")
        void testCopyForAllPieces() {
            for (ChessPieceColor color : ChessPieceColor.values()) {
                ChessPieceCopyTestHelper.assertDeepCopyBehaviorForAllPieces(List.of(
                () -> new Pawn(color, ChessboardOrientation.WHITE_BOTTOM),
                () -> new Rook(color),
                () -> new Knight(color),
                () -> new Bishop(color),
                () -> new Queen(color),
                () -> new King(color)
            ));
            }
        }
    }
}