package test.chessgame.gamelogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardInitialSetupBuilder;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.King;
import com.sdm.units.chessgame.pieces.Knight;
import com.sdm.units.chessgame.pieces.Pawn;
import com.sdm.units.chessgame.pieces.Queen;
import com.sdm.units.chessgame.pieces.Rook;

import test.chessgame.TestChessPiecePositionUtil;

public class ChessboardInitialSetupTest {

    @ParameterizedTest
    @MethodSource("whitePawnStartingPositionsProvider")
    public void isExpectedWhitePawnStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("blackPawnStartingPositionsProvider")
    public void isExpectedBlackPawnStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("whiteRookStartingPositionsProvider")
    public void isExpectedWhiteRookStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("blackRookStartingPositionsProvider")
    public void isExpectedBlackRookStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("whiteKnightStartingPositionsProvider")
    public void isExpectedWhiteKnightStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("blackKnightStartingPositionsProvider")
    public void isExpectedBlackKnightStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("whiteBishopStartingPositionsProvider")
    public void isExpectedWhiteBishopStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("blackBishopStartingPositionsProvider")
    public void isExpectedBlackBishopStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("whiteQueenStartingPositionProvider")
    public void isExpectedWhiteQueenStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("blackQueenStartingPositionProvider")
    public void isExpectedBlackQueenStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("whiteKingStartingPositionProvider")
    public void isExpectedWhiteKingStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    @ParameterizedTest
    @MethodSource("blackKingStartingPositionProvider")
    public void isExpectedBlackKingStartingPosition(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    public static List<Arguments> whitePawnStartingPositionsProvider() {
        Map<ChessboardPosition, ChessPiece> expectedWhitePawnStartingPositions = new HashMap<>();

        Stream.of(ChessboardFile.values()).forEach(
            file -> expectedWhitePawnStartingPositions.put(new ChessboardPosition(file, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE))
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhitePawnStartingPositions, ChessboardInitialSetupBuilder.getPawnStartingPositions(ChessPieceColor.WHITE));
    }

    public static List<Arguments> blackPawnStartingPositionsProvider() {
        Map<ChessboardPosition, ChessPiece> expectedBlackPawnStartingPositions = new HashMap<>();
        
        Stream.of(ChessboardFile.values()).forEach(
            file -> expectedBlackPawnStartingPositions.put(new ChessboardPosition(file, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK))
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackPawnStartingPositions, ChessboardInitialSetupBuilder.getPawnStartingPositions(ChessPieceColor.BLACK));
    }

    public static List<Arguments> whiteRookStartingPositionsProvider() {
        Map<ChessboardPosition, ChessPiece> expectedWhiteRookStartingPositions = new HashMap<>();
        
        Stream.of(ChessboardFile.A, ChessboardFile.H).forEach(
            file -> expectedWhiteRookStartingPositions.put(new ChessboardPosition(file, ChessboardRank.ONE), new Rook(ChessPieceColor.WHITE))
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhiteRookStartingPositions, ChessboardInitialSetupBuilder.getRookStartingPositions(ChessPieceColor.WHITE));
    }

    public static List<Arguments> blackRookStartingPositionsProvider() {
        Map<ChessboardPosition, ChessPiece> expectedBlackRookStartingPositions = new HashMap<>();
        
        Stream.of(ChessboardFile.A, ChessboardFile.H).forEach(
            file -> expectedBlackRookStartingPositions.put(new ChessboardPosition(file, ChessboardRank.EIGHT), new Rook(ChessPieceColor.BLACK))
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackRookStartingPositions, ChessboardInitialSetupBuilder.getRookStartingPositions(ChessPieceColor.BLACK));
    }
    
    public static List<Arguments> whiteKnightStartingPositionsProvider() {
        Map<ChessboardPosition, ChessPiece> expectedWhiteKnightStartingPositions = new HashMap<>();
        
        Stream.of(ChessboardFile.B, ChessboardFile.G).forEach(
            file -> expectedWhiteKnightStartingPositions.put(new ChessboardPosition(file, ChessboardRank.ONE), new Knight(ChessPieceColor.WHITE))
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhiteKnightStartingPositions, ChessboardInitialSetupBuilder.getKnightStartingPositions(ChessPieceColor.WHITE));
    }
    
    public static List<Arguments> blackKnightStartingPositionsProvider() {
        Map<ChessboardPosition, ChessPiece> expectedBlackKnightStartingPositions = new HashMap<>();
        
        Stream.of(ChessboardFile.B, ChessboardFile.G).forEach(
            file -> expectedBlackKnightStartingPositions.put(new ChessboardPosition(file, ChessboardRank.EIGHT), new Knight(ChessPieceColor.BLACK))
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackKnightStartingPositions, ChessboardInitialSetupBuilder.getKnightStartingPositions(ChessPieceColor.BLACK));
    }
    
    public static List<Arguments> whiteBishopStartingPositionsProvider() {
        Map<ChessboardPosition, ChessPiece> expectedWhiteBishopStartingPositions = new HashMap<>();
        
        Stream.of(ChessboardFile.C, ChessboardFile.F).forEach(
            file -> expectedWhiteBishopStartingPositions.put(new ChessboardPosition(file, ChessboardRank.ONE), new Bishop(ChessPieceColor.WHITE))
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedWhiteBishopStartingPositions, ChessboardInitialSetupBuilder.getBishopStartingPositions(ChessPieceColor.WHITE));
    }
    
    public static List<Arguments> blackBishopStartingPositionsProvider() {
        Map<ChessboardPosition, ChessPiece> expectedBlackKnightStartingPositions = new HashMap<>();
        
        Stream.of(ChessboardFile.C, ChessboardFile.F).forEach(
            file -> expectedBlackKnightStartingPositions.put(new ChessboardPosition(file, ChessboardRank.EIGHT), new Bishop(ChessPieceColor.BLACK))
        );

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedBlackKnightStartingPositions, ChessboardInitialSetupBuilder.getBishopStartingPositions(ChessPieceColor.BLACK));
    }
    
    public static List<Arguments> whiteQueenStartingPositionProvider() {
        return TestChessPiecePositionUtil.argumentsLoadProvider(Map.ofEntries(Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE), new Queen(ChessPieceColor.WHITE))), 
            ChessboardInitialSetupBuilder.getQueenStartingPosition(ChessPieceColor.WHITE));
    }
    
    public static List<Arguments> blackQueenStartingPositionProvider() {
        return TestChessPiecePositionUtil.argumentsLoadProvider(Map.ofEntries(Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT), new Queen(ChessPieceColor.BLACK))), 
            ChessboardInitialSetupBuilder.getQueenStartingPosition(ChessPieceColor.BLACK));
    }
    
    public static List<Arguments> whiteKingStartingPositionProvider() {
        return TestChessPiecePositionUtil.argumentsLoadProvider(Map.ofEntries(Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE), new King(ChessPieceColor.WHITE))), 
            ChessboardInitialSetupBuilder.getKingStartingPosition(ChessPieceColor.WHITE));
    }
    
    public static List<Arguments> blackKingStartingPositionProvider() {
        return TestChessPiecePositionUtil.argumentsLoadProvider(Map.ofEntries(Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT), new King(ChessPieceColor.BLACK))), 
            ChessboardInitialSetupBuilder.getKingStartingPosition(ChessPieceColor.BLACK));
    }
}