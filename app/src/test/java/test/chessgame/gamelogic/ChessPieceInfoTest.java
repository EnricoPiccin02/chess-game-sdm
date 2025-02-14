package test.chessgame.gamelogic;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ChessPieceInfoTest {
    
    @ParameterizedTest
    @MethodSource("chessPieceInfoValueProvider")
    public void isExpectedPieceValue(ChessPieceInfo chessPieceInfo, Integer expectedValue) {
        assertEquals(expectedValue, chessPieceInfo.getPieceValue());
    }

    @ParameterizedTest
    @MethodSource("chessPieceInfoNumberOfAllowedPiecesProvider")
    public void isExpectedNumberOfAllowedPieces(ChessPieceInfo chessPieceInfo, Integer expectedNumberOfAllowedPieces) {
        assertEquals(expectedNumberOfAllowedPieces, chessPieceInfo.getNumberOfAllowedPieces());
    }

    public static Stream<Arguments> chessPieceInfoValueProvider() {
        return Stream.of(
            arguments(ChessPieceInfo.PAWN, 1),
            arguments(ChessPieceInfo.KNIGHT, 3),
            arguments(ChessPieceInfo.BISHOP, 3),
            arguments(ChessPieceInfo.ROOK, 5),
            arguments(ChessPieceInfo.QUEEN, 9),
            arguments(ChessPieceInfo.KING, -1)
        );
    }

    public static Stream<Arguments> chessPieceInfoNumberOfAllowedPiecesProvider() {
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