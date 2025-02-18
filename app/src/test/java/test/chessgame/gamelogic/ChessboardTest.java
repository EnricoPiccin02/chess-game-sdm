package test.chessgame.gamelogic;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sdm.units.chessgame.gamelogic.Chessboard;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.King;
import com.sdm.units.chessgame.pieces.Knight;
import com.sdm.units.chessgame.pieces.Pawn;
import com.sdm.units.chessgame.pieces.Queen;
import com.sdm.units.chessgame.pieces.Rook;

public class ChessboardTest {
    
    @Test
    public void isExpectedChessboardInitialSetup() {
        Map<ChessboardPosition, Optional<ChessPiece>> expectedStartingBoard = new HashMap<>();

        Stream.of(ChessboardFile.values()).forEach(file -> {
            Stream.of(ChessboardRank.values()).forEach(rank -> {
                expectedStartingBoard.put(new ChessboardPosition(file, rank), switch (rank) {
                    case ONE, EIGHT -> switch (file) {
                        case A, H -> Optional.of(new Rook(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK));
                        case B, G -> Optional.of(new Knight(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK));
                        case C, F -> Optional.of(new Bishop(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK));
                        case D -> Optional.of(new Queen(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK));
                        case E -> Optional.of(new King(rank == ChessboardRank.ONE ? ChessPieceColor.WHITE : ChessPieceColor.BLACK));
                    };
                    case TWO, SEVEN -> Optional.of(new Pawn(rank == ChessboardRank.TWO ? ChessPieceColor.WHITE : ChessPieceColor.BLACK));
                    default -> Optional.empty();
                });
            });
        });

        assertEquals(new Chessboard().getBoard(), expectedStartingBoard);
    }
}