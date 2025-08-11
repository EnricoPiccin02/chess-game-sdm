package test.chessgame.gamelogic.movement;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import static org.mockito.Mockito.when;

public final class ChessboardMockUtils {

    private ChessboardMockUtils() {}

    public static void mockVacantPositions(Chessboard board, List<ChessboardPosition> positions) {
        positions.forEach(pos -> {
            when(board.getPieceAt(pos)).thenReturn(Optional.empty());
            when(board.isUnoccupiedSquare(pos)).thenReturn(true);
        });
    }

    public static void mockFriendlyPieceAt(Chessboard board, ChessboardPosition position, ChessPieceColor color) {
        when(board.getPieceAt(position)).thenReturn(Optional.of(new Pawn(color, ChessboardOrientation.WHITE_BOTTOM)));
        when(board.isUnoccupiedSquare(position)).thenReturn(false);
        when(board.isOpponentAt(color, position)).thenReturn(false);
    }

    public static void mockOpponentPieceAt(Chessboard board, ChessboardPosition position, ChessPieceColor friendColor, ChessPieceColor opponentColor) {
        when(board.getPieceAt(position)).thenReturn(Optional.of(new Knight(opponentColor)));
        when(board.isUnoccupiedSquare(position)).thenReturn(false);
        when(board.isOpponentAt(friendColor, position)).thenReturn(true);
    }
}