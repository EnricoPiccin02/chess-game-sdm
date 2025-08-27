package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.awt.Color;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

@DisplayName("ChessPieceColor")
class ChessPieceColorTest {

    @ParameterizedTest(name = "{0} should have name descriptor \''{1}\''")
    @MethodSource("colorNameProvider")
    @DisplayName("should have correct name descriptor")
    void shouldHaveCorrectNameDescriptor(ChessPieceColor color, String expectedName) {
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
    @DisplayName("should associate correct opponent color")
    void shouldAssociateCorrectOpponent(ChessPieceColor color, ChessPieceColor expectedOpponent) {
        assertEquals(expectedOpponent, color.opponent());
    }

    static Stream<Arguments> opponentProvider() {
        return Stream.of(
            arguments(ChessPieceColor.WHITE, ChessPieceColor.BLACK),
            arguments(ChessPieceColor.BLACK, ChessPieceColor.WHITE)
        );
    }

    @Nested
    @DisplayName("encoded color association")
    class EncodedColorAssociation {

        @Test
        @DisplayName("should associate white color for WHITE")
        void shouldAssociateWhiteColorForWhite() {
            assertEquals(Color.WHITE, ChessPieceColor.WHITE.getEncodedColor());
        }

        @Test
        @DisplayName("should associate black color for BLACK")
        void shouldAssociateBlackColorForBlack() {
            assertEquals(Color.BLACK, ChessPieceColor.BLACK.getEncodedColor());
        }
    }
}