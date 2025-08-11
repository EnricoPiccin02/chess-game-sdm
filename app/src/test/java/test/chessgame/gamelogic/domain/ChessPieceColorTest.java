package test.chessgame.gamelogic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

import java.awt.Color;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("ChessPieceColor")
class ChessPieceColorTest {

    @ParameterizedTest(name = "{0} should return name \''{1}\''")
    @MethodSource("colorNameProvider")
    @DisplayName("should return correct name")
    void shouldReturnCorrectName(ChessPieceColor color, String expectedName) {
        assertEquals(expectedName, color.toString());
    }

    static Stream<Arguments> colorNameProvider() {
        return Stream.of(
            arguments(ChessPieceColor.WHITE, "White"),
            arguments(ChessPieceColor.BLACK, "Black")
        );
    }

    @ParameterizedTest(name = "{0} opponent should be {1}")
    @MethodSource("opponentProvider")
    @DisplayName("should return correct opponent color")
    void shouldReturnCorrectOpponent(ChessPieceColor color, ChessPieceColor expectedOpponent) {
        assertEquals(expectedOpponent, color.opponent());
    }

    static Stream<Arguments> opponentProvider() {
        return Stream.of(
            arguments(ChessPieceColor.WHITE, ChessPieceColor.BLACK),
            arguments(ChessPieceColor.BLACK, ChessPieceColor.WHITE)
        );
    }

    @Nested
    @DisplayName("getEncodedColor()")
    class EncodedColorTests {

        @Test
        @DisplayName("should return Color.WHITE for WHITE")
        void shouldReturnEncodedWhiteColor() {
            assertEquals(Color.WHITE, ChessPieceColor.WHITE.getEncodedColor());
        }

        @Test
        @DisplayName("should return Color.BLACK for BLACK")
        void shouldReturnEncodedBlackColor() {
            assertEquals(Color.BLACK, ChessPieceColor.BLACK.getEncodedColor());
        }
    }
}