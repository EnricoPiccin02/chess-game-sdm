package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

@DisplayName("ChessboardOrientation")
class ChessboardOrientationTest {

    @Nested
    @DisplayName("white bottom orientation")
    class WhiteBottom {

        private ChessboardOrientation orientation;

        @BeforeEach
        void setUp() {
            orientation = ChessboardOrientation.WHITE_BOTTOM;
        }

        @Test
        @DisplayName("should place white back rank on first row")
        void shouldPlaceWhiteBackRankOnFirstRow() {
            assertEquals(ChessboardRank.ONE, orientation.getBackRank(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should place white pawns on second row")
        void shouldPlaceWhitePawnsOnSecondRow() {
            assertEquals(ChessboardRank.TWO, orientation.getPawnRank(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should place black back rank on eighth row")
        void shouldPlaceBlackBackRankOnEighthRow() {
            assertEquals(ChessboardRank.EIGHT, orientation.getBackRank(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should place black pawns on seventh row")
        void shouldPlaceBlackPawnsOnSeventhRow() {
            assertEquals(ChessboardRank.SEVEN, orientation.getPawnRank(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should move white pawns upward")
        void shouldMoveWhitePawnsUpward() {
            assertEquals(ChessboardDirection.UP, orientation.pawnForwardDirection(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should move black pawns downward")
        void shouldMoveBlackPawnsDownward() {
            assertEquals(ChessboardDirection.DOWN, orientation.pawnForwardDirection(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should allow white pawns to capture diagonally upward")
        void shouldAllowWhitePawnsToCaptureDiagonallyUpward() {
            assertEquals(Set.of(ChessboardDirection.UP_LEFT, ChessboardDirection.UP_RIGHT),
                orientation.pawnCaptureDirections(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should allow black pawns to capture diagonally downward")
        void shouldAllowBlackPawnsToCaptureDiagonallyDownward() {
            assertEquals(Set.of(ChessboardDirection.DOWN_LEFT, ChessboardDirection.DOWN_RIGHT),
                orientation.pawnCaptureDirections(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should promote white pawns on eighth row")
        void shouldPromoteWhitePawnsOnEighthRow() {
            assertEquals(ChessboardRank.EIGHT, orientation.promotionRank(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should promote black pawns on first row")
        void shouldPromoteBlackPawnsOnFirstRow() {
            assertEquals(ChessboardRank.ONE, orientation.promotionRank(ChessPieceColor.BLACK));
        }
    }

    @Nested
    @DisplayName("black bottom orientation")
    class BlackBottom {

        private ChessboardOrientation orientation;

        @BeforeEach
        void setUp() {
            orientation = ChessboardOrientation.BLACK_BOTTOM;
        }

        @Test
        @DisplayName("should place white back rank on eighth row")
        void shouldPlaceWhiteBackRankOnEighthRow() {
            assertEquals(ChessboardRank.EIGHT, orientation.getBackRank(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should place white pawns on seventh row")
        void shouldPlaceWhitePawnsOnSeventhRow() {
            assertEquals(ChessboardRank.SEVEN, orientation.getPawnRank(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should place black back rank on first row")
        void shouldPlaceBlackBackRankOnFirstRow() {
            assertEquals(ChessboardRank.ONE, orientation.getBackRank(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should place black pawns on second row")
        void shouldPlaceBlackPawnsOnSecondRow() {
            assertEquals(ChessboardRank.TWO, orientation.getPawnRank(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should move white pawns downward")
        void shouldMoveWhitePawnsDownward() {
            assertEquals(ChessboardDirection.DOWN, orientation.pawnForwardDirection(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should move black pawns upward")
        void shouldMoveBlackPawnsUpward() {
            assertEquals(ChessboardDirection.UP, orientation.pawnForwardDirection(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should allow white pawns to capture diagonally downward")
        void shouldAllowWhitePawnsToCaptureDiagonallyDownward() {
            assertEquals(Set.of(ChessboardDirection.DOWN_LEFT, ChessboardDirection.DOWN_RIGHT),
                orientation.pawnCaptureDirections(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should allow black pawns to capture diagonally upward")
        void shouldAllowBlackPawnsToCaptureDiagonallyUpward() {
            assertEquals(Set.of(ChessboardDirection.UP_LEFT, ChessboardDirection.UP_RIGHT),
                orientation.pawnCaptureDirections(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should promote white pawns on first row")
        void shouldPromoteWhitePawnsOnFirstRow() {
            assertEquals(ChessboardRank.ONE, orientation.promotionRank(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should promote black pawns on eighth row")
        void shouldPromoteBlackPawnsOnEighthRow() {
            assertEquals(ChessboardRank.EIGHT, orientation.promotionRank(ChessPieceColor.BLACK));
        }
    }
}