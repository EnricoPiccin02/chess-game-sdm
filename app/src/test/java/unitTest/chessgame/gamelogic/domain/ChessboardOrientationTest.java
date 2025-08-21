package unitTest.chessgame.gamelogic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ChessboardOrientation")
class ChessboardOrientationTest {

    @Nested
    @DisplayName("WHITE_BOTTOM orientation")
    class WhiteBottom {

        private final ChessboardOrientation orientation = ChessboardOrientation.WHITE_BOTTOM;

        @Test
        @DisplayName("should return correct back and pawn ranks for white")
        void whiteBackAndPawnRanks() {
            assertEquals(ChessboardRank.ONE, orientation.getBackRank(ChessPieceColor.WHITE));
            assertEquals(ChessboardRank.TWO, orientation.getPawnRank(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should return correct back and pawn ranks for black")
        void blackBackAndPawnRanks() {
            assertEquals(ChessboardRank.EIGHT, orientation.getBackRank(ChessPieceColor.BLACK));
            assertEquals(ChessboardRank.SEVEN, orientation.getPawnRank(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should return UP as pawn forward direction for both colors")
        void forwardDirection() {
            assertEquals(ChessboardDirection.UP, orientation.pawnForwardDirection(ChessPieceColor.WHITE));
            assertEquals(ChessboardDirection.DOWN, orientation.pawnForwardDirection(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should return correct pawn capture directions")
        void captureDirections() {
            assertEquals(List.of(ChessboardDirection.UP_LEFT, ChessboardDirection.UP_RIGHT),
                         orientation.pawnCaptureDirections(ChessPieceColor.WHITE));
            assertEquals(List.of(ChessboardDirection.DOWN_LEFT, ChessboardDirection.DOWN_RIGHT),
                         orientation.pawnCaptureDirections(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should return correct promotion rank")
        void promotionRanks() {
            assertEquals(ChessboardRank.EIGHT, orientation.promotionRank(ChessPieceColor.WHITE));
            assertEquals(ChessboardRank.ONE, orientation.promotionRank(ChessPieceColor.BLACK));
        }
    }

    @Nested
    @DisplayName("BLACK_BOTTOM orientation")
    class BlackBottom {

        private final ChessboardOrientation orientation = ChessboardOrientation.BLACK_BOTTOM;

        @Test
        @DisplayName("should return correct back and pawn ranks for white")
        void whiteBackAndPawnRanks() {
            assertEquals(ChessboardRank.EIGHT, orientation.getBackRank(ChessPieceColor.WHITE));
            assertEquals(ChessboardRank.SEVEN, orientation.getPawnRank(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should return correct back and pawn ranks for black")
        void blackBackAndPawnRanks() {
            assertEquals(ChessboardRank.ONE, orientation.getBackRank(ChessPieceColor.BLACK));
            assertEquals(ChessboardRank.TWO, orientation.getPawnRank(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should return DOWN as pawn forward direction for both colors")
        void forwardDirection() {
            assertEquals(ChessboardDirection.DOWN, orientation.pawnForwardDirection(ChessPieceColor.WHITE));
            assertEquals(ChessboardDirection.UP, orientation.pawnForwardDirection(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should return correct pawn capture directions")
        void captureDirections() {
            assertEquals(List.of(ChessboardDirection.DOWN_LEFT, ChessboardDirection.DOWN_RIGHT),
                         orientation.pawnCaptureDirections(ChessPieceColor.WHITE));
            assertEquals(List.of(ChessboardDirection.UP_LEFT, ChessboardDirection.UP_RIGHT),
                         orientation.pawnCaptureDirections(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should return correct promotion rank")
        void promotionRanks() {
            assertEquals(ChessboardRank.ONE, orientation.promotionRank(ChessPieceColor.WHITE));
            assertEquals(ChessboardRank.EIGHT, orientation.promotionRank(ChessPieceColor.BLACK));
        }
    }
}