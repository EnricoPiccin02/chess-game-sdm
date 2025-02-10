package test.chessgame.pieces;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.HashMap;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;

import test.chessgame.TestChessPiecePositionUtil;

import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardInitialSetupBuilder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PawnTest {
    
    @ParameterizedTest
    @MethodSource("whitePawnInitialMovementProvider")
    public void testWhitePawnInitialMovement(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("blackPawnInitialMovementProvider")
    public void testBlackPawnInitialMovement(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    public static List<Arguments> whitePawnInitialMovementProvider() {
        List<Arguments> testCases = new ArrayList<>();

        testCases.addAll(TestChessPiecePositionUtil.argumentsLoadProvider(
            getAllWhitePawnPossibleInitialMovements(),
            TestChessPiecePositionUtil.chessPieceMovementProvider(ChessboardInitialSetupBuilder.getPawnStartingPositions(ChessPieceColor.WHITE))
        ));

        return testCases;
    }
    
    public static List<Arguments> blackPawnInitialMovementProvider() {
        List<Arguments> testCases = new ArrayList<>();

        testCases.addAll(TestChessPiecePositionUtil.argumentsLoadProvider(
            getAllBlackPawnPossibleInitialMovements(),
            TestChessPiecePositionUtil.chessPieceMovementProvider(ChessboardInitialSetupBuilder.getPawnStartingPositions(ChessPieceColor.BLACK))
        ));

        return testCases;
    }

    private static Map<ChessboardPosition, ChessPiece> getAllWhitePawnPossibleInitialMovements() {
        Map<ChessboardPosition, ChessPiece> expectedPawnInitialPossibleLandings = new HashMap<>();
        
        Stream.of(ChessboardFile.values()).forEach(file -> {
            expectedPawnInitialPossibleLandings.put(new ChessboardPosition(file, ChessboardRank.THREE), new Pawn(ChessPieceColor.WHITE));
            expectedPawnInitialPossibleLandings.put(new ChessboardPosition(file, ChessboardRank.FOUR), new Pawn(ChessPieceColor.WHITE));
        });

        return expectedPawnInitialPossibleLandings;
    }

    private static Map<ChessboardPosition, ChessPiece> getAllBlackPawnPossibleInitialMovements() {
        Map<ChessboardPosition, ChessPiece> expectedPawnInitialPossibleLandings = new HashMap<>();
        
        Stream.of(ChessboardFile.values()).forEach(file -> {
            expectedPawnInitialPossibleLandings.put(new ChessboardPosition(file, ChessboardRank.SIX), new Pawn(ChessPieceColor.BLACK));
            expectedPawnInitialPossibleLandings.put(new ChessboardPosition(file, ChessboardRank.FIVE), new Pawn(ChessPieceColor.BLACK));
        });

        return expectedPawnInitialPossibleLandings;
    }
}