package test.chessgame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.pieces.ChessPiece;

public final class TestChessPiecePositionUtil {

    public static List<Arguments> argumentsLoadProvider(Map<ChessboardPosition, ChessPiece>  expectedPositionsChessPieces, 
        Map<ChessboardPosition, ChessPiece> actualPositionsChessPieces) throws IllegalArgumentException {
        List<Arguments> expectedActualPositions = new ArrayList<>();
        
        if (expectedPositionsChessPieces == null || actualPositionsChessPieces == null || expectedPositionsChessPieces.size() != actualPositionsChessPieces.size()) {
            throw new IllegalArgumentException("Expected and actual collections of (positions, chess pieces) should not be null and should have the same size");
        }
        
        Iterator<Map.Entry<ChessboardPosition, ChessPiece>> expectedIterator = expectedPositionsChessPieces.entrySet().stream().sorted(Map.Entry.comparingByKey()).iterator();
        Iterator<Map.Entry<ChessboardPosition, ChessPiece>> actualIterator = actualPositionsChessPieces.entrySet().stream().sorted(Map.Entry.comparingByKey()).iterator();

        while(expectedIterator.hasNext() && actualIterator.hasNext()) {
            Map.Entry<ChessboardPosition, ChessPiece> expectedEntry = expectedIterator.next();
            Map.Entry<ChessboardPosition, ChessPiece> actualEntry = actualIterator.next();
            expectedActualPositions.add(arguments(actualEntry, expectedEntry.getKey(), expectedEntry.getValue()));
        }

        return expectedActualPositions;
    }

    public static Map<ChessboardPosition, ChessPiece> chessPieceMovementProvider(Map<ChessboardPosition, ChessPiece> initialPositionsChessPieces) {
        return initialPositionsChessPieces.entrySet().stream()
            .flatMap(entry -> entry.getValue().getPossibleMoves(entry.getKey()).stream()
            .map(move -> Map.entry(move.position(), entry.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}