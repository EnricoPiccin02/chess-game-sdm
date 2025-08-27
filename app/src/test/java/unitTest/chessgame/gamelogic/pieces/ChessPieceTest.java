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
    @DisplayName("movement state")
    class MovementState {

        @Test
        @DisplayName("should not be marked as moved initially")
        void shouldNotBeMarkedAsMovedInitially() {
            assertThat(whitePiece.hasMoved()).isFalse();
        }

        @Test
        @DisplayName("should be marked as moved after calling markAsMoved")
        void shouldBeMarkedAsMovedAfterCallingMarkAsMoved() {
            whitePiece.markAsMoved();
            assertThat(whitePiece.hasMoved()).isTrue();
        }
    }

    @Nested
    @DisplayName("opponent detection")
    class OpponentDetection {

        @Test
        @DisplayName("should identify opponent when colors differ")
        void shouldIdentifyOpponentWhenColorsDiffer() {
            assertThat(whitePiece.isOpponentOf(ChessPieceColor.BLACK)).isTrue();
        }

        @Test
        @DisplayName("should not identify opponent when colors match")
        void shouldNotIdentifyOpponentWhenColorsMatch() {
            assertThat(blackPiece.isOpponentOf(ChessPieceColor.BLACK)).isFalse();
        }
    }

    @Nested
    @DisplayName("remembering and restoring state")
    class RememberingAndRestoringState {

        @Test
        @DisplayName("should remember moved state when snapshot is created")
        void shouldRememberMovedStateInSnapshot() {
            whitePiece.markAsMoved();
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();

            assertThat(snapshot.wasMoved()).isTrue();
        }

        @Test
        @DisplayName("should keep reference to original piece in snapshot")
        void shouldKeepReferenceToOriginalPieceInSnapshot() {
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();

            assertThat(snapshot.getPiece()).isSameAs(whitePiece);
        }

        @Test
        @DisplayName("should restore moved state when snapshot is applied")
        void shouldRestoreMovedStateWhenSnapshotApplied() {
            whitePiece.markAsMoved();
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();

            whitePiece.restoreSnapshot(snapshot);

            assertThat(whitePiece.hasMoved()).isTrue();
        }

        @Test
        @DisplayName("should restore unmoved state when snapshot is applied")
        void shouldRestoreUnmovedStateWhenSnapshotApplied() {
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();

            whitePiece.markAsMoved();
            whitePiece.restoreSnapshot(snapshot);

            assertThat(whitePiece.hasMoved()).isFalse();
        }

        @Test
        @DisplayName("should not affect another piece when snapshot is restored")
        void shouldNotAffectAnotherPieceWhenSnapshotRestored() {
            whitePiece.markAsMoved();
            ChessPieceSnapshot snapshot = whitePiece.createSnapshot();

            ChessPiece another = new Bishop(ChessPieceColor.WHITE);
            another.restoreSnapshot(snapshot);

            assertThat(another.hasMoved()).isFalse();
        }
    }
}