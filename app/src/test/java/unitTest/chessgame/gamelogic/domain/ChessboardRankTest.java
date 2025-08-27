package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

@DisplayName("ChessboardRank")
class ChessboardRankTest {

    @ParameterizedTest(name = "rank {0} has numeric value {1}")
    @MethodSource("rankValueProvider")
    @DisplayName("should associate correct numeric value")
    void shouldAssociateCorrectValue(ChessboardRank rank, int expectedValue) {
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
    @DisplayName("next rank computation")
    class NextRankComputation {

        @Test
        @DisplayName("should produce no rank when direction is not valid")
        void shouldProduceNoRankWhenDirectionIsNotValid() {
            assertTrue(ChessboardRank.ONE.nextRank(null).isEmpty());
        }

        @ParameterizedTest(name = "{0} from rank ONE should produce rank TWO")
        @MethodSource("validUpwardDirections")
        @DisplayName("should produce next rank when direction is valid")
        void shouldProduceValidNextRank(ChessboardDirection direction) {
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

        @ParameterizedTest(name = "{0} from rank ONE should produce rank ONE")
        @MethodSource("horizontalDirections")
        @DisplayName("should produce same rank when moving horizontally")
        void shouldProduceSameRankWhenNoVerticalChange(ChessboardDirection direction) {
            Optional<ChessboardRank> result = ChessboardRank.ONE.nextRank(direction);
            assertEquals(Optional.of(ChessboardRank.ONE), result);
        }

        static Stream<ChessboardDirection> horizontalDirections() {
            return Stream.of(
                ChessboardDirection.LEFT,
                ChessboardDirection.RIGHT
            );
        }

        @ParameterizedTest(name = "{0} from rank ONE should produce no rank")
        @MethodSource("invalidDownwardDirections")
        @DisplayName("should produce no rank when moving off board")
        void shouldProduceNoRankForOutOfBoundsDirections(ChessboardDirection direction) {
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
    @DisplayName("distance between ranks")
    class DistanceBetweenRanks {

        @Test
        @DisplayName("should compute no distance when comparing rank is not valid")
        void shouldComputeNoDistanceForInvalidRank() {
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
    @DisplayName("number to rank mapping")
    class NumberToRankMapping {

        @ParameterizedTest(name = "should map integer {0} into rank {1}")
        @MethodSource("validRankNumberProvider")
        @DisplayName("should map number to correct rank")
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

        @ParameterizedTest(name = "should not map to any rank for invalid rank {0}")
        @MethodSource("invalidRankNumberProvider")
        @DisplayName("should not map to any rank for invalid numbers")
        void shouldNotMapToAnyRankForInvalidRankNumber(int invalidValue) {
            Optional<ChessboardRank> result = ChessboardRank.valueOf(invalidValue);
            assertTrue(result.isEmpty());
        }

        static Stream<Integer> invalidRankNumberProvider() {
            return Stream.of(0, 9, -1, 99);
        }
    }
}