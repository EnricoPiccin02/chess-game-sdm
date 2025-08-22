package unittest.chessgame.gamelogic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("ChessboardDirection")
class ChessboardDirectionTest {

    @ParameterizedTest(name = "{0} should return file descriptor {1}")
    @MethodSource("fileDescriptorProvider")
    @DisplayName("should return correct directionFileDescriptor")
    void shouldReturnCorrectFileDescriptor(ChessboardDirection direction, int expectedFileDelta) {
        assertEquals(expectedFileDelta, direction.directionFileDescriptor());
    }

    static Stream<Arguments> fileDescriptorProvider() {
        return Stream.of(
            arguments(ChessboardDirection.LEFT, -1),
            arguments(ChessboardDirection.RIGHT, 1),
            arguments(ChessboardDirection.UP, 0),
            arguments(ChessboardDirection.DOWN, 0),
            arguments(ChessboardDirection.UP_LEFT, -1),
            arguments(ChessboardDirection.UP_RIGHT, 1),
            arguments(ChessboardDirection.DOWN_LEFT, -1),
            arguments(ChessboardDirection.DOWN_RIGHT, 1)
        );
    }

    @ParameterizedTest(name = "{0} should return rank descriptor {1}")
    @MethodSource("rankDescriptorProvider")
    @DisplayName("should return correct directionRankDescriptor")
    void shouldReturnCorrectRankDescriptor(ChessboardDirection direction, int expectedRankDelta) {
        assertEquals(expectedRankDelta, direction.directionRankDescriptor());
    }

    static Stream<Arguments> rankDescriptorProvider() {
        return Stream.of(
            arguments(ChessboardDirection.LEFT, 0),
            arguments(ChessboardDirection.RIGHT, 0),
            arguments(ChessboardDirection.UP, 1),
            arguments(ChessboardDirection.DOWN, -1),
            arguments(ChessboardDirection.UP_LEFT, 1),
            arguments(ChessboardDirection.UP_RIGHT, 1),
            arguments(ChessboardDirection.DOWN_LEFT, -1),
            arguments(ChessboardDirection.DOWN_RIGHT, -1)
        );
    }

    @ParameterizedTest(name = "{0} should return full descriptor [{1}, {2}]")
    @MethodSource("fullDescriptorProvider")
    @DisplayName("should return correct directionDescriptor")
    void shouldReturnCorrectFullDescriptor(ChessboardDirection direction, int expectedFileDelta, int expectedRankDelta) {
        int[] actual = direction.directionDescriptor();
        assertEquals(expectedFileDelta, actual[0]);
        assertEquals(expectedRankDelta, actual[1]);
    }

    static Stream<Arguments> fullDescriptorProvider() {
        return Stream.of(
            arguments(ChessboardDirection.LEFT, -1, 0),
            arguments(ChessboardDirection.RIGHT, 1, 0),
            arguments(ChessboardDirection.UP, 0, 1),
            arguments(ChessboardDirection.DOWN, 0, -1),
            arguments(ChessboardDirection.UP_LEFT, -1, 1),
            arguments(ChessboardDirection.UP_RIGHT, 1, 1),
            arguments(ChessboardDirection.DOWN_LEFT, -1, -1),
            arguments(ChessboardDirection.DOWN_RIGHT, 1, -1)
        );
    }
}