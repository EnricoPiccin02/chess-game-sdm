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
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;

@DisplayName("ChessboardFile")
public class ChessboardFileTest {

    @ParameterizedTest(name = "file {0} has character value \''{1}\''")
    @MethodSource("fileValueProvider")
    @DisplayName("should associate correct character value")
    void shouldAssociateCorrectValue(ChessboardFile file, char expectedChar) {
        assertEquals(expectedChar, file.value());
    }

    static Stream<Arguments> fileValueProvider() {
        return Stream.of(
            arguments(ChessboardFile.A, 'a'),
            arguments(ChessboardFile.B, 'b'),
            arguments(ChessboardFile.C, 'c'),
            arguments(ChessboardFile.D, 'd'),
            arguments(ChessboardFile.E, 'e'),
            arguments(ChessboardFile.F, 'f'),
            arguments(ChessboardFile.G, 'g'),
            arguments(ChessboardFile.H, 'h')
        );
    }

    @Nested
    @DisplayName("next file computation")
    class NextFileComputation {

        @Test
        @DisplayName("should produce no file when direction is not valid")
        void shouldReturnNoFileWhenDirectionIsNotValid() {
            assertTrue(ChessboardFile.A.nextFile(null).isEmpty());
        }

        @ParameterizedTest(name = "{0} from file A should produce file B")
        @MethodSource("validForwardDirections")
        @DisplayName("should produce next file when direction is valid")
        void shouldProduceValidNextFile(ChessboardDirection direction) {
            Optional<ChessboardFile> result = ChessboardFile.A.nextFile(direction);
            assertEquals(Optional.of(ChessboardFile.B), result);
        }

        static Stream<ChessboardDirection> validForwardDirections() {
            return Stream.of(
                ChessboardDirection.RIGHT,
                ChessboardDirection.UP_RIGHT,
                ChessboardDirection.DOWN_RIGHT
            );
        }

        @ParameterizedTest(name = "{0} from file A should produce file A")
        @MethodSource("sameFileDirections")
        @DisplayName("should produce same file when moving vertically")
        void shouldProduceSameFileWhenNoHorizontalChange(ChessboardDirection direction) {
            Optional<ChessboardFile> result = ChessboardFile.A.nextFile(direction);
            assertEquals(Optional.of(ChessboardFile.A), result);
        }

        static Stream<ChessboardDirection> sameFileDirections() {
            return Stream.of(
                ChessboardDirection.UP,
                ChessboardDirection.DOWN
            );
        }

        @ParameterizedTest(name = "{0} from file A should produce no file")
        @MethodSource("invalidBackwardDirections")
        @DisplayName("should produce no file when moving off board")
        void shouldProduceNoFileForOutOfBoundsDirections(ChessboardDirection direction) {
            Optional<ChessboardFile> result = ChessboardFile.A.nextFile(direction);
            assertTrue(result.isEmpty());
        }

        static Stream<ChessboardDirection> invalidBackwardDirections() {
            return Stream.of(
                ChessboardDirection.LEFT,
                ChessboardDirection.UP_LEFT,
                ChessboardDirection.DOWN_LEFT
            );
        }
    }

    @Nested
    @DisplayName("distance between files")
    class DistanceBetweenFiles {

        @Test
        @DisplayName("should compute no distance when comparing file is not valid")
        void shouldComputeNoDistanceForInvalidFile() {
            assertTrue(ChessboardFile.D.distance(null).isEmpty());
        }

        @ParameterizedTest(name = "distance from {0} to {1} = {2}")
        @MethodSource("fileDistanceProvider")
        @DisplayName("should compute correct file distance")
        void shouldReturnCorrectDistance(ChessboardFile from, ChessboardFile to, int expected) {
            OptionalInt result = from.distance(to);
            assertTrue(result.isPresent());
            assertEquals(expected, result.getAsInt());
        }

        static Stream<Arguments> fileDistanceProvider() {
            return Stream.of(
                arguments(ChessboardFile.A, ChessboardFile.A, 0),
                arguments(ChessboardFile.B, ChessboardFile.A, 1),
                arguments(ChessboardFile.A, ChessboardFile.B, -1),
                arguments(ChessboardFile.H, ChessboardFile.E, 3),
                arguments(ChessboardFile.C, ChessboardFile.H, -5)
            );
        }
    }

    @Nested
    @DisplayName("character to file mapping")
    class CharacterToFileMapping {

        @ParameterizedTest(name = "should map character \''{0}\'' into file {1}")
        @MethodSource("validCharProvider")
        @DisplayName("should map character to correct file")
        void shouldMapCharacterToCorrectFile(char inputChar, ChessboardFile expectedFile) {
            Optional<ChessboardFile> result = ChessboardFile.valueOf(inputChar);
            assertTrue(result.isPresent());
            assertEquals(expectedFile, result.get());
        }

        static Stream<Arguments> validCharProvider() {
            return Stream.of(
                arguments('a', ChessboardFile.A),
                arguments('b', ChessboardFile.B),
                arguments('c', ChessboardFile.C),
                arguments('d', ChessboardFile.D),
                arguments('e', ChessboardFile.E),
                arguments('f', ChessboardFile.F),
                arguments('g', ChessboardFile.G),
                arguments('h', ChessboardFile.H)
            );
        }

        @ParameterizedTest(name = "should not map to any file for invalid character \''{0}\''")
        @MethodSource("invalidCharProvider")
        @DisplayName("should not map to any file for invalid characters")
        void shouldNotMapToAnyFileForInvalidCharacter(char inputChar) {
            Optional<ChessboardFile> result = ChessboardFile.valueOf(inputChar);
            assertTrue(result.isEmpty());
        }

        static Stream<Character> invalidCharProvider() {
            return Stream.of('i', 'z', '1', 'A', '@', ' ');
        }
    }
}