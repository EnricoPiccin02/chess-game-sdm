package test.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.gui.ChessBoardViewModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardViewModelTest {

    private final ChessboardPosition testPosition =
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);

    @Test
    void testChessBoardViewModelConstruction() {
        List<ChessBoardViewModel.SquareViewModel> squares = new ArrayList<>();
        ChessBoardViewModel viewModel = new ChessBoardViewModel(squares);

        assertNotNull(viewModel);
        assertEquals(squares, viewModel.squares());
        assertTrue(viewModel.squares().isEmpty());
    }

    @Test
    void testChessBoardViewModelWithSquares() {
        List<ChessBoardViewModel.SquareViewModel> squares = new ArrayList<>();
        ChessBoardViewModel.PieceViewModel piece =
                new ChessBoardViewModel.PieceViewModel("♔", true);
        ChessBoardViewModel.SquareViewModel square =
                new ChessBoardViewModel.SquareViewModel(testPosition, piece);
        squares.add(square);

        ChessBoardViewModel viewModel = new ChessBoardViewModel(squares);

        assertEquals(1, viewModel.squares().size());
        assertEquals(square, viewModel.squares().getFirst());
    }

    @Test
    void testSquareViewModelConstruction() {
        ChessBoardViewModel.PieceViewModel piece =
                new ChessBoardViewModel.PieceViewModel("♔", true);
        ChessBoardViewModel.SquareViewModel square =
                new ChessBoardViewModel.SquareViewModel(testPosition, piece);

        assertNotNull(square);
        assertEquals(testPosition, square.position());
        assertEquals(piece, square.piece());
    }

    @Test
    void testSquareViewModelWithNullPiece() {
        ChessBoardViewModel.SquareViewModel square =
                new ChessBoardViewModel.SquareViewModel(testPosition, null);

        assertNotNull(square);
        assertEquals(testPosition, square.position());
        assertNull(square.piece());
    }

    @Test
    void testPieceViewModelConstruction() {
        ChessBoardViewModel.PieceViewModel piece =
                new ChessBoardViewModel.PieceViewModel("♔", true);

        assertNotNull(piece);
        assertEquals("♔", piece.symbol());
        assertTrue(piece.isWhite());
    }

    @Test
    void testPieceViewModelBlackPiece() {
        ChessBoardViewModel.PieceViewModel piece =
                new ChessBoardViewModel.PieceViewModel("♚", false);

        assertNotNull(piece);
        assertEquals("♚", piece.symbol());
        assertFalse(piece.isWhite());
    }

    @Test
    void testEquality() {
        ChessBoardViewModel.PieceViewModel piece1 =
                new ChessBoardViewModel.PieceViewModel("♔", true);
        ChessBoardViewModel.PieceViewModel piece2 =
                new ChessBoardViewModel.PieceViewModel("♔", true);

        assertEquals(piece1, piece2);

        ChessBoardViewModel.SquareViewModel square1 =
                new ChessBoardViewModel.SquareViewModel(testPosition, piece1);
        ChessBoardViewModel.SquareViewModel square2 =
                new ChessBoardViewModel.SquareViewModel(testPosition, piece2);

        assertEquals(square1, square2);

        List<ChessBoardViewModel.SquareViewModel> squares1 = List.of(square1);
        List<ChessBoardViewModel.SquareViewModel> squares2 = List.of(square2);

        ChessBoardViewModel viewModel1 = new ChessBoardViewModel(squares1);
        ChessBoardViewModel viewModel2 = new ChessBoardViewModel(squares2);

        assertEquals(viewModel1, viewModel2);
    }

    @Test
    void testInequalityDifferentPieces() {
        ChessBoardViewModel.PieceViewModel piece1 =
                new ChessBoardViewModel.PieceViewModel("♔", true);
        ChessBoardViewModel.PieceViewModel piece2 =
                new ChessBoardViewModel.PieceViewModel("♕", true);

        assertNotEquals(piece1, piece2);
    }

    @Test
    void testInequalityDifferentColors() {
        ChessBoardViewModel.PieceViewModel piece1 =
                new ChessBoardViewModel.PieceViewModel("♔", true);
        ChessBoardViewModel.PieceViewModel piece2 =
                new ChessBoardViewModel.PieceViewModel("♔", false);

        assertNotEquals(piece1, piece2);
    }

    @Test
    void testCompleteBoard() {
        List<ChessBoardViewModel.SquareViewModel> squares = new ArrayList<>();
        // Create a complete 8x8 board
        for (ChessboardFile file : ChessboardFile.values()) {
            for (ChessboardRank rank : ChessboardRank.values()) {
                ChessboardPosition position = new ChessboardPosition(file, rank);
                ChessBoardViewModel.SquareViewModel square =
                        new ChessBoardViewModel.SquareViewModel(position, null);
                squares.add(square);
            }
        }

        ChessBoardViewModel viewModel = new ChessBoardViewModel(squares);
        assertEquals(64, viewModel.squares().size());
    }

    @Test
    void testHashCode() {
        ChessBoardViewModel.PieceViewModel piece =
                new ChessBoardViewModel.PieceViewModel("♔", true);
        ChessBoardViewModel.SquareViewModel square =
                new ChessBoardViewModel.SquareViewModel(testPosition, piece);
        List<ChessBoardViewModel.SquareViewModel> squares = List.of(square);
        ChessBoardViewModel viewModel = new ChessBoardViewModel(squares);

        // Create identical objects
        ChessBoardViewModel.PieceViewModel piece2 =
                new ChessBoardViewModel.PieceViewModel("♔", true);
        ChessBoardViewModel.SquareViewModel square2 =
                new ChessBoardViewModel.SquareViewModel(testPosition, piece2);
        List<ChessBoardViewModel.SquareViewModel> squares2 = List.of(square2);
        ChessBoardViewModel viewModel2 = new ChessBoardViewModel(squares2);

        assertEquals(viewModel.hashCode(), viewModel2.hashCode());
    }

    @Test
    void testToString() {
        ChessBoardViewModel.PieceViewModel piece =
                new ChessBoardViewModel.PieceViewModel("♔", true);

        String pieceString = piece.toString();
        assertTrue(pieceString.contains("♔"));
        assertTrue(pieceString.contains("true"));

        ChessBoardViewModel.SquareViewModel square =
                new ChessBoardViewModel.SquareViewModel(testPosition, piece);
        String squareString = square.toString();
        assertTrue(squareString.contains(testPosition.toString()));
        assertTrue(squareString.contains(piece.toString()));
    }
}
