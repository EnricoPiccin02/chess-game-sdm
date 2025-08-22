package unittest.chessgame.gamelogic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("ChessboardRank")
class ChessboardRankTest {

    @ParameterizedTest(name = "rank {0} has numeric value {1}")
    @MethodSource("rankValueProvider")
    @DisplayName("should return correct numeric value")
    void shouldReturnCorrectValue(ChessboardRank rank, int expectedValue) {
        assertEquals(expectedValue, rank.value());
    }

    static Stream<Arguments> rankValueProvider() {
        return Stream.of(
            arguments(ChessboardRank.ONE, 1),
            arguments(ChessboardRank.TWO, 2),
            arguments(ChessboardRank.THREE, 3),
            arguments(ChessboardRank.FOUR, 4),
            arguments(ChessboardRank.FIVE, 5),
            arguments(ChessboardRank.SIX, 6),
            arguments(ChessboardRank.SEVEN, 7),
            arguments(ChessboardRank.EIGHT, 8)
        );
    }

    @Nested
    @DisplayName("nextRank()")
    class NextRankTests {

        @Test
        @DisplayName("should return empty when direction is null")
        void shouldReturnEmptyWhenDirectionIsNull() {
            assertTrue(ChessboardRank.ONE.nextRank(null).isEmpty());
        }

        @ParameterizedTest(name = "{0} from rank ONE should return rank TWO")
        @MethodSource("validUpwardDirections")
        @DisplayName("should return next rank when direction is valid")
        void shouldReturnValidNextRank(ChessboardDirection direction) {
            Optional<ChessboardRank> result = ChessboardRank.ONE.nextRank(direction);
            assertEquals(Optional.of(ChessboardRank.TWO), result);
        }

        static Stream<ChessboardDirection> validUpwardDirections() {
            return Stream.of(
                ChessboardDirection.UP,
                ChessboardDirection.UP_LEFT,
                ChessboardDirection.UP_RIGHT
            );
        }

        @ParameterizedTest(name = "{0} from rank ONE should return rank ONE")
        @MethodSource("horizontalDirections")
        @DisplayName("should return same rank when moving horizontally")
        void shouldReturnSameRankWhenNoVerticalChange(ChessboardDirection direction) {
            Optional<ChessboardRank> result = ChessboardRank.ONE.nextRank(direction);
            assertEquals(Optional.of(ChessboardRank.ONE), result);
        }

        static Stream<ChessboardDirection> horizontalDirections() {
            return Stream.of(
                ChessboardDirection.LEFT,
                ChessboardDirection.RIGHT
            );
        }

        @ParameterizedTest(name = "{0} from rank ONE should return empty")
        @MethodSource("invalidDownwardDirections")
        @DisplayName("should return empty when moving off board")
        void shouldReturnEmptyForOutOfBoundsDirections(ChessboardDirection direction) {
            Optional<ChessboardRank> result = ChessboardRank.ONE.nextRank(direction);
            assertTrue(result.isEmpty());
        }

        static Stream<ChessboardDirection> invalidDownwardDirections() {
            return Stream.of(
                ChessboardDirection.DOWN,
                ChessboardDirection.DOWN_LEFT,
                ChessboardDirection.DOWN_RIGHT
            );
        }
    }

    @Nested
    @DisplayName("distance()")
    class DistanceTests {

        @Test
        @DisplayName("should return empty when argument is null")
        void shouldReturnEmptyForNullArgument() {
            assertTrue(ChessboardRank.THREE.distance(null).isEmpty());
        }

        @ParameterizedTest(name = "distance from {0} to {1} = {2}")
        @MethodSource("rankDistanceProvider")
        @DisplayName("should compute correct rank distance")
        void shouldReturnCorrectDistance(ChessboardRank from, ChessboardRank to, int expectedDistance) {
            OptionalInt result = from.distance(to);
            assertTrue(result.isPresent());
            assertEquals(expectedDistance, result.getAsInt());
        }

        static Stream<Arguments> rankDistanceProvider() {
            return Stream.of(
                arguments(ChessboardRank.ONE, ChessboardRank.ONE, 0),
                arguments(ChessboardRank.TWO, ChessboardRank.ONE, -1),
                arguments(ChessboardRank.ONE, ChessboardRank.TWO, 1),
                arguments(ChessboardRank.SIX, ChessboardRank.FOUR, -2),
                arguments(ChessboardRank.THREE, ChessboardRank.EIGHT, 5)
            );
        }
    }

    @Nested
    @DisplayName("valueOf(Integer)")
    class ValueOfIntegerTests {

        @ParameterizedTest(name = "should parse integer {0} into rank {1}")
        @MethodSource("validRankNumberProvider")
        @DisplayName("should map number to correct ChessboardRank")
        void shouldMapNumberToCorrectRank(int value, ChessboardRank expectedRank) {
            Optional<ChessboardRank> result = ChessboardRank.valueOf(value);
            assertTrue(result.isPresent());
            assertEquals(expectedRank, result.get());
        }

        static Stream<Arguments> validRankNumberProvider() {
            return Stream.of(
                arguments(1, ChessboardRank.ONE),
                arguments(2, ChessboardRank.TWO),
                arguments(3, ChessboardRank.THREE),
                arguments(4, ChessboardRank.FOUR),
                arguments(5, ChessboardRank.FIVE),
                arguments(6, ChessboardRank.SIX),
                arguments(7, ChessboardRank.SEVEN),
                arguments(8, ChessboardRank.EIGHT)
            );
        }

        @ParameterizedTest(name = "should return empty for invalid rank {0}")
        @MethodSource("invalidRankNumberProvider")
        @DisplayName("should return empty for invalid numbers")
        void shouldReturnEmptyForInvalidRankNumber(int invalidValue) {
            Optional<ChessboardRank> result = ChessboardRank.valueOf(invalidValue);
            assertTrue(result.isEmpty());
        }

        static Stream<Integer> invalidRankNumberProvider() {
            return Stream.of(0, 9, -1, 99);
        }
    }
}