package test.chessgame.gamelogic;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import test.chessgame.TestChessPiecePositionUtil;

public class ChessboardTest {
    
    @ParameterizedTest
    @MethodSource("chessboardInitialSetupProvider")
    public void isExpectedChessboardInitialSetup(Map.Entry<ChessboardPosition, ChessPiece> chessboardSquare, ChessboardPosition expectedPosition, ChessPiece expectedPiece) {
        assertEquals(expectedPosition, chessboardSquare.getKey());
        assertEquals(expectedPiece, chessboardSquare.getValue());
    }

    private static List<Arguments> chessboardInitialSetupProvider() {
        Map<ChessboardPosition, ChessPiece> actualStartingBoard = new Chessboard().getBoard();
        Map<ChessboardPosition, ChessPiece> expectedStartingBoard = new HashMap<>();

        Stream.of(ChessboardFile.values()).forEach(file -> {
            Stream.of(ChessboardRank.values()).forEach(rank -> {
                expectedStartingBoard.put(new ChessboardPosition(file, rank), switch (rank) {
                    case ONE, EIGHT -> switch (file) {
                        case A, H -> new Rook(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK);
                        case B, G -> new Knight(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK);
                        case C, F -> new Bishop(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK);
                        case D -> new Queen(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK);
                        case E -> new King(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK);
                        default -> null;
                    };
                    case TWO, SEVEN -> new Pawn(rank == ChessboardRank.TWO ? ChessPieceColor.WHITE : ChessPieceColor.BLACK);
                    default -> null;
                });
            });
        });

        return TestChessPiecePositionUtil.argumentsLoadProvider(expectedStartingBoard, actualStartingBoard);
    }
}