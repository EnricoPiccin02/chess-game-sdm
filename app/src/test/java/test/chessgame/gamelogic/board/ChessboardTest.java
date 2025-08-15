package test.chessgame.gamelogic.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.BoardStateManager;
import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.initialization.ChessboardSetup;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;

@DisplayName("Chessboard")
class ChessboardTest {

    private Chessboard board;
    private ChessPiece whitePawn;
    private ChessPiece blackQueen;
    private ChessboardPosition a1;
    private ChessboardPosition h8;

    @BeforeEach
    void setUp() {
        ChessboardOrientation orientation = ChessboardOrientation.WHITE_BOTTOM;
        ChessboardSetup setup = Mockito.mock(ChessboardSetup.class);

        Map<ChessboardPosition, ChessPiece> emptyBoard = new HashMap<>();
        when(setup.generate(orientation)).thenReturn(emptyBoard);

        board = new BoardStateManager(orientation, setup);

        whitePawn = new Pawn(ChessPieceColor.WHITE, orientation);
        blackQueen = new Queen(ChessPieceColor.BLACK);

        a1 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        h8 = new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT);
    }

    @Nested
    @DisplayName("getOccupiedSquaresOf(color)")
    class GetOccupiedSquaresOf {

        @Test
        @DisplayName("should return empty list when no pieces of that color are on the board")
        void shouldReturnEmptyWhenNoPiecesOfColor() {
            assertThat(board.getOccupiedSquaresOf(ChessPieceColor.WHITE)).isEmpty();
        }

        @Test
        @DisplayName("should return all squares occupied by given color")
        void shouldReturnSquaresForColor() {
            board.putPieceAt(a1, whitePawn);
            board.putPieceAt(h8, blackQueen);

            List<ChessboardPosition> whiteSquares = board.getOccupiedSquaresOf(ChessPieceColor.WHITE);

            assertThat(whiteSquares).containsExactly(a1);
        }
    }

    @Nested
    @DisplayName("isUnoccupiedSquare(position)")
    class IsUnoccupiedSquare {

        @Test
        @DisplayName("should return true for empty square")
        void shouldReturnTrueForEmptySquare() {
            assertThat(board.isUnoccupiedSquare(a1)).isTrue();
        }

        @Test
        @DisplayName("should return false for occupied square")
        void shouldReturnFalseForOccupiedSquare() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.isUnoccupiedSquare(a1)).isFalse();
        }
    }

    @Nested
    @DisplayName("getPieceAt(position)")
    class GetPieceAt {

        @Test
        @DisplayName("should return empty optional when square is empty")
        void shouldReturnEmptyWhenEmpty() {
            assertThat(board.getPieceAt(a1)).isEmpty();
        }

        @Test
        @DisplayName("should return piece when square is occupied")
        void shouldReturnPieceWhenOccupied() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.getPieceAt(a1)).contains(whitePawn);
        }
    }

    @Nested
    @DisplayName("isOpponentAt(playerColor, position)")
    class IsOpponentAt {

        @Test
        @DisplayName("should return false when square is empty")
        void shouldReturnFalseWhenEmpty() {
            assertThat(board.isOpponentAt(ChessPieceColor.WHITE, a1)).isFalse();
        }

        @Test
        @DisplayName("should return false when piece is same color")
        void shouldReturnFalseWhenFriendlyPiece() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.isOpponentAt(ChessPieceColor.WHITE, a1)).isFalse();
        }

        @Test
        @DisplayName("should return true when piece is opponent color")
        void shouldReturnTrueWhenOpponentPiece() {
            board.putPieceAt(a1, blackQueen);
            assertThat(board.isOpponentAt(ChessPieceColor.WHITE, a1)).isTrue();
        }
    }

    @Nested
    @DisplayName("mutations")
    class BoardMutations {

        @Test
        @DisplayName("putPieceAt should place piece at given position")
        void putPieceAtShouldPlacePiece() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.getPieceAt(a1)).contains(whitePawn);
        }

        @Test
        @DisplayName("removePieceAt should remove piece at given position")
        void removePieceAtShouldRemovePiece() {
            board.putPieceAt(a1, whitePawn);
            board.removePieceAt(a1);
            assertThat(board.getPieceAt(a1)).isEmpty();
        }
    }

    @Nested
    @DisplayName("resetBoard()")
    class ResetBoard {

        @Test
        @DisplayName("should restore board to initial setup")
        void shouldRestoreToInitialSetup() {
            Map<ChessboardPosition, ChessPiece> initialSetup = Map.of(
                a1, whitePawn,
                h8, blackQueen
            );
            ChessboardSetup setup = Mockito.mock(ChessboardSetup.class);
            when(setup.generate(ChessboardOrientation.WHITE_BOTTOM)).thenReturn(initialSetup);

            Chessboard customBoard = new BoardStateManager(ChessboardOrientation.WHITE_BOTTOM, setup);

            customBoard.removePieceAt(a1);

            customBoard.resetBoard();

            assertThat(customBoard.getPieceAt(a1)).contains(whitePawn);
            assertThat(customBoard.getPieceAt(h8)).contains(blackQueen);
        }
    }

    @Nested
    @DisplayName("deepCopy()")
    class DeepCopy {

        @Test
        @DisplayName("should create a copy with identical pieces but independent objects")
        void shouldCreateIndependentCopy() {
            board.putPieceAt(a1, whitePawn);

            Chessboard copy = board.deepCopy();

            assertThat(copy.getPieceAt(a1)).contains(whitePawn);
            assertThat(copy).isNotSameAs(board);
            assertThat(copy.getPieceAt(a1).get()).isNotSameAs(board.getPieceAt(a1).get());
        }
    }
}