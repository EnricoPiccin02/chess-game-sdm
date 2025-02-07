package test.chessgame.gamelogic;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.sdm.units.chessgame.gamelogic.ChessboardRank;

public class ChessboardRankTest {
    
    @ParameterizedTest
    @MethodSource("chessboardRankValueProvider")
    public void isExpectedRankValue(ChessboardRank chessboardRank, Integer expectedValue) {
        assertEquals(expectedValue, chessboardRank.value());
    }

    public static Stream<Arguments> chessboardRankValueProvider() {
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
}