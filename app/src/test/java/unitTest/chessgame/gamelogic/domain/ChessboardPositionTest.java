package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

@DisplayName("ChessboardPosition")
class ChessboardPositionTest {

    private ChessboardPosition d4;
    private ChessboardPosition e5;
    private ChessboardPosition h8;

    @BeforeEach
    void setUp() {
        d4 = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR);
        e5 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);
        h8 = new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT);
    }

    @Nested
    @DisplayName("navigating the board in one direction")
    class SingleDirectionNavigation {

        @Test
        @DisplayName("should move upwards")
        void shouldMoveUpwards() {
            Optional<ChessboardPosition> result = d4.nextPosition(ChessboardDirection.UP);
            assertEquals(new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE), result.get());
        }

        @Test
        @DisplayName("should move diagonally")
        void shouldMoveDiagonally() {
            Optional<ChessboardPosition> result = d4.nextPosition(ChessboardDirection.UP_RIGHT);
            assertEquals(e5, result.get());
        }

        @Test
        @DisplayName("should stop movement at the edge when moving beyond it")
        void shouldStopAtEdgeBeyondIt() {
            Optional<ChessboardPosition> result = h8.nextPosition(ChessboardDirection.UP_RIGHT);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("navigating the board through multiple directions")
    class MultipleDirectionNavigation {

        @Test
        @DisplayName("should follow sequence of directions")
        void shouldFollowSequenceUpThenRight() {
            Optional<ChessboardPosition> result = d4.nextPosition(List.of(
                ChessboardDirection.UP, ChessboardDirection.RIGHT));
            assertEquals(e5, result.get());
        }

        @Test
        @DisplayName("should halt sequence when moving beyond the board")
        void shouldHaltWhenMovingBeyondBoard() {
            Optional<ChessboardPosition> result = h8.nextPosition(List.of(
                ChessboardDirection.UP, ChessboardDirection.RIGHT));
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("checking diagonals")
    class DiagonalCheck {

        @Test
        @DisplayName("should recognize close diagonal positions as diagonal")
        void shouldRecognizeCloseDiagonalPositions() {
            assertTrue(d4.isDiagonalTo(e5));
        }

        @Test
        @DisplayName("should recognize far away diagonal positions as diagonal")
        void shouldRecognizeFarDiagonalPositions() {
            assertTrue(d4.isDiagonalTo(h8));
        }

        @Test
        @DisplayName("should not consider diagonal relation with missing position")
        void shouldNotConsiderMissingPositionAsDiagonal() {
            assertTrue(!d4.isDiagonalTo(null));
        }
    }

    @Nested
    @DisplayName("string representation")
    class StringRepresentation {

        @Test
        @DisplayName("should display positions properly")
        void shouldDisplayAsReadableString() {
            assertEquals("(D, 4)", d4.toString());
        }
    }
}