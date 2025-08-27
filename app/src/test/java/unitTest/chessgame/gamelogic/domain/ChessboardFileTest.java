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
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;

@DisplayName("ChessboardFile")
class ChessboardFileTest {

    @Nested
    @DisplayName("file indexing")
    class FileIndexing {

        @ParameterizedTest(name = "{0} should have index {1}")
        @MethodSource("fileIndexProvider")
        @DisplayName("should associate file with correct index")
        void shouldAssociateFileWithCorrectIndex(ChessboardFile file, int expectedIndex) {
            assertEquals(expectedIndex, file.index());
        }

        static Stream<Arguments> fileIndexProvider() {
            return Stream.of(
                arguments(ChessboardFile.A, 0),
                arguments(ChessboardFile.B, 1),
                arguments(ChessboardFile.C, 2),
                arguments(ChessboardFile.D, 3),
                arguments(ChessboardFile.E, 4),
                arguments(ChessboardFile.F, 5),
                arguments(ChessboardFile.G, 6),
                arguments(ChessboardFile.H, 7)
            );
        }
    }

    @Nested
    @DisplayName("next file computation")
    class NextFileComputation {

        @ParameterizedTest(name = "{0} from file A should advance to file B")
        @MethodSource("forwardDirections")
        @DisplayName("should advance file when moving forward")
        void shouldAdvanceFileWhenMovingForward(ChessboardDirection direction) {
            Optional<ChessboardFile> result = ChessboardFile.A.nextFile(direction);
            assertEquals(Optional.of(ChessboardFile.B), result);
        }

        @ParameterizedTest(name = "{0} from file A should remain at file A")
        @MethodSource("verticalDirections")
        @DisplayName("should keep same file when moving vertically")
        void shouldKeepSameFileWhenMovingVertically(ChessboardDirection direction) {
            Optional<ChessboardFile> result = ChessboardFile.A.nextFile(direction);
            assertEquals(Optional.of(ChessboardFile.A), result);
        }

        @ParameterizedTest(name = "{0} from file A should move off the board")
        @MethodSource("backwardDirections")
        @DisplayName("should not advance file when moving off the board")
        void shouldNotAdvanceFileWhenMovingOffTheBoard(ChessboardDirection direction) {
            Optional<ChessboardFile> result = ChessboardFile.A.nextFile(direction);
            assertTrue(result.isEmpty());
        }

        static Stream<ChessboardDirection> forwardDirections() {
            return Stream.of(
                ChessboardDirection.RIGHT,
                ChessboardDirection.UP_RIGHT,
                ChessboardDirection.DOWN_RIGHT
            );
        }

        static Stream<ChessboardDirection> verticalDirections() {
            return Stream.of(
                ChessboardDirection.UP,
                ChessboardDirection.DOWN
            );
        }

        static Stream<ChessboardDirection> backwardDirections() {
            return Stream.of(
                ChessboardDirection.LEFT,
                ChessboardDirection.UP_LEFT,
                ChessboardDirection.DOWN_LEFT
            );
        }
    }

    @Nested
    @DisplayName("character to file mapping")
    class CharacterToFileMapping {

        @ParameterizedTest(name = "should map character '{0}' to file {1}")
        @MethodSource("validCharProvider")
        @DisplayName("should map valid characters to files")
        void shouldMapValidCharactersToFiles(char inputChar, ChessboardFile expectedFile) {
            Optional<ChessboardFile> result = ChessboardFile.of(inputChar);
            assertEquals(Optional.of(expectedFile), result);
        }

        @ParameterizedTest(name = "should not map character '{0}' to any file")
        @MethodSource("invalidCharProvider")
        @DisplayName("should reject invalid characters")
        void shouldRejectInvalidCharacters(char inputChar) {
            Optional<ChessboardFile> result = ChessboardFile.of(inputChar);
            assertTrue(result.isEmpty());
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
                arguments('h', ChessboardFile.H),
                // Uppercase mappings included
                arguments('A', ChessboardFile.A),
                arguments('H', ChessboardFile.H)
            );
        }

        static Stream<Character> invalidCharProvider() {
            return Stream.of('i', 'z', '1', '@', ' ');
        }
    }

    @Nested
    @DisplayName("index to file mapping")
    class IndexToFileMapping {

        @ParameterizedTest(name = "should map index {0} to file {1}")
        @MethodSource("validIndexProvider")
        @DisplayName("should map valid indexes to files")
        void shouldMapValidIndexesToFiles(int index, ChessboardFile expectedFile) {
            Optional<ChessboardFile> result = ChessboardFile.of(index);
            assertEquals(Optional.of(expectedFile), result);
        }

        @ParameterizedTest(name = "should not map index {0} to any file")
        @MethodSource("invalidIndexProvider")
        @DisplayName("should reject invalid indexes")
        void shouldRejectInvalidIndexes(int invalidIndex) {
            Optional<ChessboardFile> result = ChessboardFile.of(invalidIndex);
            assertTrue(result.isEmpty());
        }

        static Stream<Arguments> validIndexProvider() {
            return Stream.of(
                arguments(0, ChessboardFile.A),
                arguments(1, ChessboardFile.B),
                arguments(2, ChessboardFile.C),
                arguments(3, ChessboardFile.D),
                arguments(4, ChessboardFile.E),
                arguments(5, ChessboardFile.F),
                arguments(6, ChessboardFile.G),
                arguments(7, ChessboardFile.H)
            );
        }

        static Stream<Integer> invalidIndexProvider() {
            return Stream.of(-1, 8, 99);
        }
    }

    @Nested
    @DisplayName("string representation")
    class StringRepresentation {

        @Test
        @DisplayName("should display file descriptor properly")
        void shouldDisplayAsReadableString() {
            assertEquals("H", ChessboardFile.H.toString());
        }
    }
}