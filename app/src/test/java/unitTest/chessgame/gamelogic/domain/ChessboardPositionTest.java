package unittest.chessgame.gamelogic.domain;

import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ChessboardPosition")
class ChessboardPositionTest {

    private final ChessboardPosition d4 = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR);
    private final ChessboardPosition e5 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);
    private final ChessboardPosition h8 = new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT);

    @Nested
    @DisplayName("nextPosition(direction)")
    class NextPositionSingleDirection {

        @Test
        @DisplayName("should return correct position when moving UP")
        void shouldMoveUp() {
            Optional<ChessboardPosition> result = d4.nextPosition(ChessboardDirection.UP);
            assertTrue(result.isPresent());
            assertEquals(new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE), result.get());
        }

        @Test
        @DisplayName("should return correct position when moving UP_RIGHT")
        void shouldMoveUpRight() {
            Optional<ChessboardPosition> result = d4.nextPosition(ChessboardDirection.UP_RIGHT);
            assertTrue(result.isPresent());
            assertEquals(e5, result.get());
        }

        @Test
        @DisplayName("should return empty when next position is outside board")
        void shouldReturnEmptyOutsideBoard() {
            Optional<ChessboardPosition> result = h8.nextPosition(ChessboardDirection.UP_RIGHT);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("should return empty if file or rank is null")
        void shouldReturnEmptyWithNullFileOrRank() {
            ChessboardPosition invalid = new ChessboardPosition(null, ChessboardRank.FOUR);
            assertTrue(invalid.nextPosition(ChessboardDirection.UP).isEmpty());
        }
    }

    @Nested
    @DisplayName("nextPosition(directions)")
    class NextPositionMultipleDirections {

        @Test
        @DisplayName("should follow multiple directions to reach final position")
        void shouldFollowMultipleDirections() {
            Optional<ChessboardPosition> result = d4.nextPosition(List.of(
                ChessboardDirection.UP, ChessboardDirection.RIGHT));
            assertTrue(result.isPresent());
            assertEquals(e5, result.get());
        }

        @Test
        @DisplayName("should return empty if any step leads outside the board")
        void shouldStopIfStepIsInvalid() {
            Optional<ChessboardPosition> result = h8.nextPosition(List.of(
                ChessboardDirection.UP, ChessboardDirection.RIGHT));
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("distance(other)")
    class DistanceBetweenPositions {

        @Test
        @DisplayName("should return correct Manhattan distance")
        void shouldReturnCorrectDistance() {
            OptionalInt distance = d4.distance(e5);
            assertTrue(distance.isPresent());
            assertEquals(2, distance.getAsInt());
        }

        @Test
        @DisplayName("should return empty when one position is null")
        void shouldReturnEmptyIfOtherIsNull() {
            assertTrue(d4.distance(null).isEmpty());
        }

        @Test
        @DisplayName("should return empty if rank or file is null")
        void shouldReturnEmptyIfFileOrRankIsNull() {
            ChessboardPosition invalid = new ChessboardPosition(null, ChessboardRank.FOUR);
            assertTrue(invalid.distance(d4).isEmpty());
        }
    }

    @Nested
    @DisplayName("compareTo(other)")
    class CompareToOtherPosition {

        @Test
        @DisplayName("should return 0 when positions are equal")
        void shouldReturnZeroWhenEqual() {
            assertEquals(0, d4.compareTo(d4));
        }

        @Test
        @DisplayName("should return positive if this is before other")
        void shouldReturnPositiveIfBefore() {
            assertTrue(d4.compareTo(e5) > 0);
        }

        @Test
        @DisplayName("should return negative if this is after other")
        void shouldReturnNegativeIfAfter() {
            assertTrue(e5.compareTo(d4) < 0);
        }

        @Test
        @DisplayName("should return 0 when compared to null or incomplete")
        void shouldReturnZeroIfNull() {
            ChessboardPosition incomplete = new ChessboardPosition(null, ChessboardRank.ONE);
            assertEquals(0, incomplete.compareTo(d4));
            assertEquals(0, d4.compareTo(null));
        }
    }
}