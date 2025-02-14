package test.chessgame.gamelogic;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ChessboardFileTest {
    
    @ParameterizedTest
    @MethodSource("chessboardFileValueProvider")
    public void isExpectedFileValue(ChessboardFile chessboardFile, Character expectedValue) {
        assertEquals(expectedValue, chessboardFile.value());
    }

    public static Stream<Arguments> chessboardFileValueProvider() {
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
}