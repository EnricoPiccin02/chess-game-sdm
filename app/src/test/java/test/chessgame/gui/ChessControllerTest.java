package test.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.GameLogic;
import com.sdm.units.chessgame.gui.ChessBoardView;
import com.sdm.units.chessgame.gui.ChessBoardViewModel;
import com.sdm.units.chessgame.gui.ChessController;
import com.sdm.units.chessgame.gui.ChessMove;
import com.sdm.units.chessgame.pieces.ChessPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChessControllerTest {

    private GameLogic gameLogic;
    private ChessBoardView chessBoardView;
    private ChessController chessController;

    // Common positions used in tests
    private final ChessboardPosition D2 = new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO);
    private final ChessboardPosition D4 = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR);
    private final ChessPiece mockPawn = mock(ChessPiece.class); // Mock a ChessPiece

    @BeforeEach
    public void setUp() {
        gameLogic = mock(GameLogic.class);
        chessBoardView = mock(ChessBoardView.class);
        chessController = new ChessController(chessBoardView, gameLogic);
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
    public void firstClickOnEmptySquareDoesNothingTest() {
        when(gameLogic.isMovable(D2)).thenReturn(false);

        chessController.handleSquareClick(D2);

        verify(chessBoardView, never()).highlightSquare(any());
    }

    @Test
    public void firstClickHighlightsSquareIfMovablePieceTest() {
        when(gameLogic.isMovable(D2)).thenReturn(true);

        chessController.handleSquareClick(D2);

        verify(chessBoardView).highlightSquare(D2);
    }

    @Test
    public void secondClickOnSameSquareUnhighlightSelectionTest() {
        when(gameLogic.isMovable(D2)).thenReturn(true);

        chessController.handleSquareClick(D2);
        chessController.handleSquareClick(D2);

        verify(chessBoardView).clearHighlights();
    }

    @Test
    public void secondClickInvalidMoveUnhighlightSelectionTest() {
        when(gameLogic.isMovable(D2)).thenReturn(true);

        chessController.handleSquareClick(D2);


        when(gameLogic.isValidMove(new ChessMove(D2, D4, gameLogic.getPieceAt(D2)))).thenReturn(false);

        chessController.handleSquareClick(D4);

        verify(chessBoardView).clearHighlights();
    }

    @Test
    public void secondClickValidMoveUnhighlightSelectionTest() {
        when(gameLogic.isMovable(D2)).thenReturn(true);

        chessController.handleSquareClick(D2);

        when(gameLogic.isValidMove(new ChessMove(D2, D4, gameLogic.getPieceAt(D2)))).thenReturn(true);

        chessController.handleSquareClick(D4);

        verify(chessBoardView).clearHighlights();
    }

    @Test
    void testValidMoveUpdatesBoard() {
        ChessboardPosition start = mock(ChessboardPosition.class);
        ChessboardPosition target = mock(ChessboardPosition.class);

        when(gameLogic.isMovable(D2)).thenReturn(true);
        when(gameLogic.getPieceAt(D2)).thenReturn(Optional.of(mockPawn));
        when(gameLogic.isValidMove(new ChessMove(D2, D4, gameLogic.getPieceAt(D2)))).thenReturn(true);

        // Act
        chessController.handleSquareClick(D2);  // Select piece
        chessController.handleSquareClick(D4); // Attempt move

        // Assert
        verify(chessBoardView, times(1)).updateBoard(any(ChessBoardViewModel.class));
    }

    @Test
    void testValidMoveCallsMakeMove() {
        // Arrange
        ChessboardPosition start = mock(ChessboardPosition.class);
        ChessboardPosition target = mock(ChessboardPosition.class);
        ChessPiece mockPawn = mock(ChessPiece.class);

        when(gameLogic.isMovable(start)).thenReturn(true);
        when(gameLogic.getPieceAt(start)).thenReturn(Optional.of(mockPawn));
        when(gameLogic.isValidMove(any(ChessMove.class))).thenReturn(true);

        // Act
        chessController.handleSquareClick(start);
        chessController.handleSquareClick(target);

        // Capture the move passed to makeMove
        ArgumentCaptor<ChessMove> moveCaptor = ArgumentCaptor.forClass(ChessMove.class);
        verify(gameLogic, times(1)).makeMove(moveCaptor.capture());

        // Assert that the move has the correct start and target positions
        ChessMove capturedMove = moveCaptor.getValue();
        assertEquals(start, capturedMove.getStart());
        assertEquals(target, capturedMove.getTarget());
    }

}