package test.chessgame.gamelogic;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardInitialSetup;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;

import test.chessgame.TestChessPiecePositionUtil;

public class ChessboardInitialSetupTest {

    @ParameterizedTest
    @MethodSource("whitePawnStartingPositionsProvider")
    public void isExpectedWhitePawnStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("blackPawnStartingPositionsProvider")
    public void isExpectedBlackPawnStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("whiteRookStartingPositionsProvider")
    public void isExpectedWhiteRookStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("blackRookStartingPositionsProvider")
    public void isExpectedBlackRookStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("whiteKnightStartingPositionsProvider")
    public void isExpectedWhiteKnightStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("blackKnightStartingPositionsProvider")
    public void isExpectedBlackKnightStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("whiteBishopStartingPositionsProvider")
    public void isExpectedWhiteBishopStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("blackBishopStartingPositionsProvider")
    public void isExpectedBlackBishopStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("whiteQueenStartingPositionProvider")
    public void isExpectedWhiteQueenStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("blackQueenStartingPositionProvider")
    public void isExpectedBlackQueenStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("whiteKingStartingPositionProvider")
    public void isExpectedWhiteKingStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    @ParameterizedTest
    @MethodSource("blackKingStartingPositionProvider")
    public void isExpectedBlackKingStartingPosition(ChessboardPosition position, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, position);
    }

    public static List<Arguments> whitePawnStartingPositionsProvider() {
        List<ChessboardPosition> expectedWhitePawnStartingPositions = List.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.C, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.F, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.G, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.H, ChessboardRank.TWO)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhitePawnStartingPositions, ChessboardInitialSetup.getPawnStartingPositions(ChessPieceColor.WHITE));
    }

    public static List<Arguments> blackPawnStartingPositionsProvider() {
        List<ChessboardPosition> expectedBlackPawnStartingPositions = List.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.SEVEN),
            new ChessboardPosition(ChessboardFile.B, ChessboardRank.SEVEN),
            new ChessboardPosition(ChessboardFile.C, ChessboardRank.SEVEN),
            new ChessboardPosition(ChessboardFile.D, ChessboardRank.SEVEN),
            new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN),
            new ChessboardPosition(ChessboardFile.F, ChessboardRank.SEVEN),
            new ChessboardPosition(ChessboardFile.G, ChessboardRank.SEVEN),
            new ChessboardPosition(ChessboardFile.H, ChessboardRank.SEVEN)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackPawnStartingPositions, ChessboardInitialSetup.getPawnStartingPositions(ChessPieceColor.BLACK));
    }

    public static List<Arguments> whiteRookStartingPositionsProvider() {
        List<ChessboardPosition> expectedWhiteRookStartingPositions = List.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE),
            new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhiteRookStartingPositions, ChessboardInitialSetup.getRookStartingPositions(ChessPieceColor.WHITE));
    }

    public static List<Arguments> blackRookStartingPositionsProvider() {
        List<ChessboardPosition> expectedBlackRookStartingPositions = List.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT),
            new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackRookStartingPositions, ChessboardInitialSetup.getRookStartingPositions(ChessPieceColor.BLACK));
    }

    public static List<Arguments> whiteKnightStartingPositionsProvider() {
        List<ChessboardPosition> expectedWhiteKnightStartingPositions = List.of(
            new ChessboardPosition(ChessboardFile.B, ChessboardRank.ONE),
            new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhiteKnightStartingPositions, ChessboardInitialSetup.getKnightStartingPositions(ChessPieceColor.WHITE));
    }

    public static List<Arguments> blackKnightStartingPositionsProvider() {
        List<ChessboardPosition> expectedBlackKnightStartingPositions = List.of(
            new ChessboardPosition(ChessboardFile.B, ChessboardRank.EIGHT),
            new ChessboardPosition(ChessboardFile.G, ChessboardRank.EIGHT)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackKnightStartingPositions, ChessboardInitialSetup.getKnightStartingPositions(ChessPieceColor.BLACK));
    }

    public static List<Arguments> whiteBishopStartingPositionsProvider() {
        List<ChessboardPosition> expectedWhiteBishopStartingPositions = List.of(
            new ChessboardPosition(ChessboardFile.C, ChessboardRank.ONE),
            new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhiteBishopStartingPositions, ChessboardInitialSetup.getBishopStartingPositions(ChessPieceColor.WHITE));
    }

    public static List<Arguments> blackBishopStartingPositionsProvider() {
        List<ChessboardPosition> expectedBlackBishopStartingPositions = List.of(
            new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT),
            new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackBishopStartingPositions, ChessboardInitialSetup.getBishopStartingPositions(ChessPieceColor.BLACK));
    }

    public static List<Arguments> whiteQueenStartingPositionProvider() {
        List<ChessboardPosition> expectedWhiteQueenStartingPosition = List.of(
            new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhiteQueenStartingPosition, List.of(ChessboardInitialSetup.getQueenStartingPosition(ChessPieceColor.WHITE)));
    }

    public static List<Arguments> blackQueenStartingPositionProvider() {
        List<ChessboardPosition> expectedBlackQueenStartingPosition = List.of(
            new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackQueenStartingPosition, List.of(ChessboardInitialSetup.getQueenStartingPosition(ChessPieceColor.BLACK)));
    }

    public static List<Arguments> whiteKingStartingPositionProvider() {
        List<ChessboardPosition> expectedWhiteKingStartingPosition = List.of(
            new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhiteKingStartingPosition, List.of(ChessboardInitialSetup.getKingStartingPosition(ChessPieceColor.WHITE)));
    }

    public static List<Arguments> blackKingStartingPositionProvider() {
        List<ChessboardPosition> expectedBlackKingStartingPosition = List.of(
            new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT)
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackKingStartingPosition, List.of(ChessboardInitialSetup.getKingStartingPosition(ChessPieceColor.BLACK)));
    }
}