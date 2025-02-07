package test.chessgame.gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.Chessboard;
import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.King;
import com.sdm.units.chessgame.pieces.Knight;
import com.sdm.units.chessgame.pieces.Pawn;
import com.sdm.units.chessgame.pieces.Queen;
import com.sdm.units.chessgame.pieces.Rook;

public class ChessboardTest {
    
    @ParameterizedTest
    @MethodSource("chessboardInitialSetupProvider")
    public void isExpectedChessboardInitialSetup(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    private static List<Arguments> chessboardInitialSetupProvider() {
        List<Arguments> arguments = new ArrayList<>();
        Map<ChessboardPosition, ChessPiece> actualStartingBoard = new Chessboard().getBoard();
        Map<ChessboardPosition, ChessPiece> expectedStartingBoard = getExpectedStartingBoard();

        for (Map.Entry<ChessboardPosition, ChessPiece> entry : actualStartingBoard.entrySet()) {
            ChessboardPosition position = entry.getKey();
            
            if (expectedStartingBoard.containsKey(position)) {
                ChessPiece expectedPiece = expectedStartingBoard.get(position);
                arguments.add(arguments(entry, position, expectedPiece));
            } else {
                arguments.add(arguments(entry, position, null));
            }
        }

        return arguments;
    }

    private static Map<ChessboardPosition, ChessPiece> getExpectedStartingBoard() {
        return Map.ofEntries(
            Map.entry(new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE), new Rook(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.B, ChessboardRank.ONE), new Knight(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.C, ChessboardRank.ONE), new Bishop(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE), new Queen(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE), new King(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE), new Bishop(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE), new Knight(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE), new Rook(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.C, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.F, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.G, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.H, ChessboardRank.TWO), new Pawn(ChessPieceColor.WHITE)),
            Map.entry(new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT), new Rook(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.B, ChessboardRank.EIGHT), new Knight(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT), new Bishop(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT), new Queen(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT), new King(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT), new Bishop(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.G, ChessboardRank.EIGHT), new Knight(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT), new Rook(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.A, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.B, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.C, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.F, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.G, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK)),
            Map.entry(new ChessboardPosition(ChessboardFile.H, ChessboardRank.SEVEN), new Pawn(ChessPieceColor.BLACK))
        );
    }
}