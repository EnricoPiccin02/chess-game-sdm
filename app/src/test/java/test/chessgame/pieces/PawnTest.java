package test.chessgame.pieces;

import java.util.List;
import java.util.ArrayList;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.pieces.Pawn;

import test.chessgame.TestChessPiecePositionUtil;

import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.ChessboardInitialSetup;
import com.sdm.units.chessgame.gamelogic.ChessPieceMove;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PawnTest {
    
    @ParameterizedTest
    @MethodSource("whitePawnInitialMovementProvider")
    public void testWhitePawnInitialMovement(ChessboardPosition actualPosition, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, actualPosition);
    }

    @ParameterizedTest
    @MethodSource("blackPawnInitialMovementProvider")
    public void testBlackPawnInitialMovement(ChessboardPosition actualPosition, ChessboardPosition expectedPosition) {
        assertEquals(expectedPosition, actualPosition);
    }

    public static List<Arguments> whitePawnInitialMovementProvider() {
        List<Arguments> testCases = new ArrayList<>();
        
        ChessboardInitialSetup.getPawnStartingPositions(ChessPieceColor.WHITE).forEach(
            position -> {
                testCases.addAll(TestChessPiecePositionUtil.argumentsLoadProvider(
                    List.of(position.nexPosition(ChessboardDirection.UP),
                        position.nexPosition(ChessboardDirection.UP, ChessboardDirection.UP)),
                    new Pawn(ChessPieceColor.WHITE).getPossibleMoves(position)
                        .stream()
                        .map(ChessPieceMove::position).toList())
                );
            }
        );

        return testCases;
    }

    public static List<Arguments> blackPawnInitialMovementProvider() {
        List<Arguments> testCases = new ArrayList<>();
        
        ChessboardInitialSetup.getPawnStartingPositions(ChessPieceColor.BLACK).forEach(
            position -> {
                testCases.addAll(TestChessPiecePositionUtil.argumentsLoadProvider(
                    List.of(position.nexPosition(ChessboardDirection.DOWN),
                        position.nexPosition(ChessboardDirection.DOWN, ChessboardDirection.DOWN)),
                    new Pawn(ChessPieceColor.BLACK).getPossibleMoves(position)
                        .stream()
                        .map(ChessPieceMove::position).toList())
                );
            }
        );

        return testCases;
    }
}