package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;

@DisplayName("ChessboardDirection")
class ChessboardDirectionTest {

    @ParameterizedTest(name = "{0} should associate file descriptor {1}")
    @MethodSource("fileDescriptorProvider")
    @DisplayName("should associate correct file descriptor")
    void shouldAssociateCorrectFileDescriptor(ChessboardDirection direction, int expectedFileDelta) {
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

    @ParameterizedTest(name = "{0} should associate rank descriptor {1}")
    @MethodSource("rankDescriptorProvider")
    @DisplayName("should associate correct rank descriptor")
    void shouldAssociateCorrectRankDescriptor(ChessboardDirection direction, int expectedRankDelta) {
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

    @ParameterizedTest(name = "{0} should associate full descriptor [{1}, {2}]")
    @MethodSource("fullDescriptorProvider")
    @DisplayName("should associate correct full descriptor")
    void shouldAssociateCorrectFullDescriptor(ChessboardDirection direction, int expectedFileDelta, int expectedRankDelta) {
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