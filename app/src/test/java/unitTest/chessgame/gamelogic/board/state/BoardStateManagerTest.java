package unittest.chessgame.gamelogic.board.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.BoardStateManager;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.initialization.ChessboardSetup;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;

@DisplayName("BoardStateManager")
class BoardStateManagerTest {

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
    @DisplayName("retrieving occupied squares")
    class RetrieveOccupiedSquares {

        @Test
        @DisplayName("should not get any square when no pieces of given color are on board")
        void shouldNotGetAnySquareWhenNoPiecesOfGivenColorAreOnBoard() {
            assertThat(board.getOccupiedSquaresOf(ChessPieceColor.WHITE)).isEmpty();
        }

        @Test
        @DisplayName("should identify the only square occupied by color")
        void shouldIdentifyTheOnlySquareOccupiedByColor() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.getOccupiedSquaresOf(ChessPieceColor.WHITE)).containsExactly(a1);
        }

        @Test
        @DisplayName("should not identify as occupied squares occupied by different color")
        void shouldNotIdentifyAsOccupiedSquaresOccupiedByDifferentColor() {
            board.putPieceAt(h8, blackQueen);
            assertThat(board.getOccupiedSquaresOf(ChessPieceColor.WHITE)).isEmpty();
        }
    }

    @Nested
    @DisplayName("checking unoccupied squares")
    class CheckingUnoccupiedSquare {

        @Test
        @DisplayName("should identify empty square")
        void shouldIdentifyEmptySquare() {
            assertThat(board.isUnoccupiedSquare(a1)).isTrue();
        }

        @Test
        @DisplayName("should identify occupied square")
        void shouldIdentifyOccupiedSquare() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.isUnoccupiedSquare(a1)).isFalse();
        }
    }

    @Nested
    @DisplayName("retrieving pieces from board")
    class RetrievingPiecesFromBoard {

        @Test
        @DisplayName("should not get any piece when square is vacant")
        void shouldNotGetAnyPieceWhenSquareIsVacant() {
            assertThat(board.getPieceAt(a1)).isEmpty();
        }

        @Test
        @DisplayName("should get piece when square is occupied")
        void shouldGetPieceWhenSquareIsOccupied() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.getPieceAt(a1)).contains(whitePawn);
        }
    }

    @Nested
    @DisplayName("checking opponent pieces")
    class CheckingOpponentPieces {

        @Test
        @DisplayName("should not recognize an opponent when square is vacant")
        void shouldNotRecognizeOpponentWhenSquareIsVacant() {
            assertThat(board.isOpponentAt(ChessPieceColor.WHITE, a1)).isFalse();
        }

        @Test
        @DisplayName("should recognize friendly piece when piece is same color")
        void shouldRecognizeFriendlyPieceWhenSameColor() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.isOpponentAt(ChessPieceColor.WHITE, a1)).isFalse();
        }

        @Test
        @DisplayName("should recognize opponent piece when piece is opponent color")
        void shouldRecognizeOpponentPieceWhenOpponentColor() {
            board.putPieceAt(a1, blackQueen);
            assertThat(board.isOpponentAt(ChessPieceColor.WHITE, a1)).isTrue();
        }
    }

    @Nested
    @DisplayName("mutations")
    class BoardMutations {

        @Test
        @DisplayName("should place piece on board")
        void shouldPlacePieceOnBoard() {
            board.putPieceAt(a1, whitePawn);
            assertThat(board.getPieceAt(a1)).contains(whitePawn);
        }

        @Test
        @DisplayName("should remove piece from board")
        void shouldRemovePieceFromBoard() {
            board.putPieceAt(a1, whitePawn);
            board.removePieceAt(a1);
            assertThat(board.getPieceAt(a1)).isEmpty();
        }
    }

    @Nested
    @DisplayName("reset the board")
    class ResetBoard {

        private Chessboard customBoard;

        @BeforeEach
        void setUpCustomBoard() {
            Map<ChessboardPosition, ChessPiece> initialSetup = Map.of(
                a1, whitePawn,
                h8, blackQueen
            );
            ChessboardSetup setup = Mockito.mock(ChessboardSetup.class);
            when(setup.generate(ChessboardOrientation.WHITE_BOTTOM)).thenReturn(initialSetup);

            customBoard = new BoardStateManager(ChessboardOrientation.WHITE_BOTTOM, setup);
        }

        @Test
        @DisplayName("should restore white pawn to its starting position")
        void shouldRestoreWhitePawn() {
            customBoard.removePieceAt(a1);
            customBoard.resetBoard();
            assertThat(customBoard.getPieceAt(a1)).contains(whitePawn);
        }

        @Test
        @DisplayName("should restore black queen to its starting position")
        void shouldRestoreBlackQueen() {
            customBoard.removePieceAt(h8);
            customBoard.resetBoard();
            assertThat(customBoard.getPieceAt(h8)).contains(blackQueen);
        }
    }
}