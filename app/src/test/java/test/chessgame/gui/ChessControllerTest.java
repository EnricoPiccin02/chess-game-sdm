package test.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.GameLogic;
import com.sdm.units.chessgame.gui.ChessBoardView;
import com.sdm.units.chessgame.gui.ChessController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChessControllerTest {

    private GameLogic gameLogic;
    private ChessBoardView chessBoardView;
    private ChessController chessController;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        gameLogic = mock(GameLogic.class);
        chessBoardView = mock(ChessBoardView.class);

        // Initialize the object under test
        chessController = new ChessController(gameLogic, chessBoardView);
    }

    @Test
    public void constructorAssignGameLogic() {
        assertEquals(gameLogic, chessController.getGameLogic());
    }

    @Test
    public void constructorAssignChessBoardView() {
        assertEquals(chessBoardView, chessController.getChessBoardView());
    }

    @Test
    public void firstClickHighlightsSquareIfMovablePieceTest() {
        // Assume the piece at D2 is movable
        when(gameLogic.isMovable(ChessboardFile.D, ChessboardRank.TWO)).thenReturn(true);

        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.TWO);

        // Verify that the view highlights the square
        verify(chessBoardView).highlightSquare(ChessboardFile.D, ChessboardRank.TWO);
    }

    @Test
    public void secondClickOnSameSquareUnhighlightSelectionTest() {
        // Assume the piece at D2 is movable
        when(gameLogic.isMovable(ChessboardFile.D, ChessboardRank.TWO)).thenReturn(true);


        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.TWO);
        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.TWO);

        // Verify that the view highlights the square
        verify(chessBoardView).clearHighlights();
    }

    @Test
    public void secondClickInvalidMoveUnhighlightSelectionTest() {
        // Assume the piece at D2 is movable
        when(gameLogic.isMovable(ChessboardFile.D, ChessboardRank.TWO)).thenReturn(true);

        // First click selects a piece
        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.TWO);

        // Assume move to D4 is invalid
        when(gameLogic.isValidMove(ChessboardFile.D, ChessboardRank.TWO, ChessboardFile.D, ChessboardRank.FOUR))
                .thenReturn(false);

        // Second click attempts an invalid move
        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.FOUR);

        // Verify that the selection is unhighlighted
        verify(chessBoardView).clearHighlights();
    }

    @Test
    public void secondClickValidMoveUnhighlightSelectionTest() {
        // Assume the piece at D2 is movable
        when(gameLogic.isMovable(ChessboardFile.D, ChessboardRank.TWO)).thenReturn(true);

        // First click selects a piece
        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.TWO);

        // Assume move to D4 is invalid
        when(gameLogic.isValidMove(ChessboardFile.D, ChessboardRank.TWO, ChessboardFile.D, ChessboardRank.FOUR))
                .thenReturn(true);

        // Second click attempts an invalid move
        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.FOUR);

        // Verify that the selection is unhighlighted
        verify(chessBoardView).clearHighlights();
    }

    @Test
    public void secondClickValidMoveTest() {
        // Assume the piece at D2 is movable and the move to D4 is valid
        when(gameLogic.isMovable(ChessboardFile.D, ChessboardRank.TWO)).thenReturn(true);
        when(gameLogic.isValidMove(ChessboardFile.D, ChessboardRank.TWO, ChessboardFile.D, ChessboardRank.FOUR)).thenReturn(true);

        // First click selects the piece
        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.TWO);

        // Second click on a valid square (D4)
        chessController.handleSquareClick(ChessboardFile.D, ChessboardRank.FOUR);

        // Verify that the move was processed
        verify(gameLogic).makeMove(ChessboardFile.D, ChessboardRank.TWO, ChessboardFile.D, ChessboardRank.FOUR);

        // Verify that the board view is updated
        verify(chessBoardView).updateBoard();

        // Verify that highlights are cleared after the valid move
        verify(chessBoardView).clearHighlights();
    }






}


