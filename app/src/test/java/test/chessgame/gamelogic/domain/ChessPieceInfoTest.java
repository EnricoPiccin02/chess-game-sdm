package test.chessgame.gamelogic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("ChessPieceInfo")
class ChessPieceInfoTest {

    @ParameterizedTest(name = "{0} should return name \''{1}\''")
    @MethodSource("nameProvider")
    @DisplayName("should return correct name via toString()")
    void shouldReturnCorrectName(ChessPieceInfo piece, String expectedName) {
        assertEquals(expectedName, piece.toString());
    }

    static Stream<Arguments> nameProvider() {
        return Stream.of(
            arguments(ChessPieceInfo.PAWN, "Pawn"),
            arguments(ChessPieceInfo.KNIGHT, "Knight"),
            arguments(ChessPieceInfo.BISHOP, "Bishop"),
            arguments(ChessPieceInfo.ROOK, "Rook"),
            arguments(ChessPieceInfo.QUEEN, "Queen"),
            arguments(ChessPieceInfo.KING, "King")
        );
    }

    @ParameterizedTest(name = "{0} should have value {1}")
    @MethodSource("valueProvider")
    @DisplayName("should return correct piece value")
    void shouldReturnCorrectValue(ChessPieceInfo piece, int expectedValue) {
        assertEquals(expectedValue, piece.getPieceValue());
    }

    static Stream<Arguments> valueProvider() {
        return Stream.of(
            arguments(ChessPieceInfo.PAWN, 1),
            arguments(ChessPieceInfo.KNIGHT, 3),
            arguments(ChessPieceInfo.BISHOP, 3),
            arguments(ChessPieceInfo.ROOK, 5),
            arguments(ChessPieceInfo.QUEEN, 9),
            arguments(ChessPieceInfo.KING, -1)
        );
    }

    @ParameterizedTest(name = "{0} should allow {1} piece(s)")
    @MethodSource("allowedCountProvider")
    @DisplayName("should return correct number of allowed pieces")
    void shouldReturnCorrectAllowedCount(ChessPieceInfo piece, int expectedCount) {
        assertEquals(expectedCount, piece.getNumberOfAllowedPieces());
    }

    static Stream<Arguments> allowedCountProvider() {
        return Stream.of(
            arguments(ChessPieceInfo.PAWN, 8),
            arguments(ChessPieceInfo.KNIGHT, 2),
            arguments(ChessPieceInfo.BISHOP, 2),
            arguments(ChessPieceInfo.ROOK, 2),
            arguments(ChessPieceInfo.QUEEN, 1),
            arguments(ChessPieceInfo.KING, 1)
        );
    }
}
