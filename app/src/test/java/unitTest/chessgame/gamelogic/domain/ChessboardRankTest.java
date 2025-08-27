package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Optional;
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
    
    @Nested
    @DisplayName("rank indexing")
    class RankIndexing {

        @ParameterizedTest(name = "{0} should have index {1}")
        @MethodSource("rankIndexProvider")
        @DisplayName("should associate rank with correct index")
        void shouldAssociateRankWithCorrectIndex(ChessboardRank rank, int expectedIndex) {
            assertEquals(expectedIndex, rank.index());
        }

        static Stream<Arguments> rankIndexProvider() {
            return Stream.of(
                arguments(ChessboardRank.ONE, 0),
                arguments(ChessboardRank.TWO, 1),
                arguments(ChessboardRank.THREE, 2),
                arguments(ChessboardRank.FOUR, 3),
                arguments(ChessboardRank.FIVE, 4),
                arguments(ChessboardRank.SIX, 5),
                arguments(ChessboardRank.SEVEN, 6),
                arguments(ChessboardRank.EIGHT, 7)
            );
        }
    }

    @Nested
    @DisplayName("next rank computation")
    class NextRankComputation {

        @ParameterizedTest(name = "{0} from rank ONE should advance to rank TWO")
        @MethodSource("upwardDirections")
        @DisplayName("should advance rank when moving upward")
        void shouldAdvanceRankWhenMovingUpward(ChessboardDirection direction) {
            Optional<ChessboardRank> result = ChessboardRank.ONE.nextRank(direction);
            assertEquals(Optional.of(ChessboardRank.TWO), result);
        }

        @ParameterizedTest(name = "{0} from rank ONE should remain at rank ONE")
        @MethodSource("horizontalDirections")
        @DisplayName("should keep same rank when moving horizontally")
        void shouldKeepSameRankWhenMovingHorizontally(ChessboardDirection direction) {
            Optional<ChessboardRank> result = ChessboardRank.ONE.nextRank(direction);
            assertEquals(Optional.of(ChessboardRank.ONE), result);
        }

        @ParameterizedTest(name = "{0} from rank ONE should move off the board")
        @MethodSource("downwardDirections")
        @DisplayName("should not advance rank when moving off the board")
        void shouldNotAdvanceRankWhenMovingOffTheBoard(ChessboardDirection direction) {
            Optional<ChessboardRank> result = ChessboardRank.ONE.nextRank(direction);
            assertTrue(result.isEmpty());
        }

        static Stream<ChessboardDirection> upwardDirections() {
            return Stream.of(
                ChessboardDirection.UP,
                ChessboardDirection.UP_LEFT,
                ChessboardDirection.UP_RIGHT
            );
        }

        static Stream<ChessboardDirection> horizontalDirections() {
            return Stream.of(
                ChessboardDirection.LEFT,
                ChessboardDirection.RIGHT
            );
        }

        static Stream<ChessboardDirection> downwardDirections() {
            return Stream.of(
                ChessboardDirection.DOWN,
                ChessboardDirection.DOWN_LEFT,
                ChessboardDirection.DOWN_RIGHT
            );
        }
    }

    @Nested
    @DisplayName("number to rank mapping")
    class NumberToRankMapping {

        @ParameterizedTest(name = "should map number {0} to rank {1}")
        @MethodSource("validNumberProvider")
        @DisplayName("should map valid numbers to ranks")
        void shouldMapValidNumbersToRanks(int number, ChessboardRank expectedRank) {
            Optional<ChessboardRank> result = ChessboardRank.ofNumber(number);
            assertEquals(Optional.of(expectedRank), result);
        }

        @ParameterizedTest(name = "should not map number {0} to any rank")
        @MethodSource("invalidNumberProvider")
        @DisplayName("should reject invalid numbers")
        void shouldRejectInvalidNumbers(int invalidNumber) {
            Optional<ChessboardRank> result = ChessboardRank.ofNumber(invalidNumber);
            assertTrue(result.isEmpty());
        }

        static Stream<Arguments> validNumberProvider() {
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

        static Stream<Integer> invalidNumberProvider() {
            return Stream.of(0, 9, -1, 99);
        }
    }

    @Nested
    @DisplayName("index to rank mapping")
    class IndexToRankMapping {

        @ParameterizedTest(name = "should map index {0} to rank {1}")
        @MethodSource("validIndexProvider")
        @DisplayName("should map valid indices to ranks")
        void shouldMapValidIndicesToRanks(int index, ChessboardRank expectedRank) {
            Optional<ChessboardRank> result = ChessboardRank.ofIndex(index);
            assertEquals(Optional.of(expectedRank), result);
        }

        @ParameterizedTest(name = "should not map index {0} to any rank")
        @MethodSource("invalidIndexProvider")
        @DisplayName("should reject invalid indices")
        void shouldRejectInvalidIndices(int invalidIndex) {
            Optional<ChessboardRank> result = ChessboardRank.ofIndex(invalidIndex);
            assertTrue(result.isEmpty());
        }

        static Stream<Arguments> validIndexProvider() {
            return Stream.of(
                arguments(0, ChessboardRank.ONE),
                arguments(1, ChessboardRank.TWO),
                arguments(2, ChessboardRank.THREE),
                arguments(3, ChessboardRank.FOUR),
                arguments(4, ChessboardRank.FIVE),
                arguments(5, ChessboardRank.SIX),
                arguments(6, ChessboardRank.SEVEN),
                arguments(7, ChessboardRank.EIGHT)
            );
        }

        static Stream<Integer> invalidIndexProvider() {
            return Stream.of(-1, 8);
        }
    }

    @Nested
    @DisplayName("string representation")
    class StringRepresentation {

        @Test
        @DisplayName("should display rank descriptor properly")
        void shouldDisplayAsReadableString() {
            assertEquals("7", ChessboardRank.SEVEN.toString());
        }
    }
}