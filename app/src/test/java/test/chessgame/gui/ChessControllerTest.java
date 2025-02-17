package test.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.GameLogic;
import com.sdm.units.chessgame.gui.ChessBoardView;
import com.sdm.units.chessgame.gui.ChessController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChessControllerTest {

    private GameLogic gameLogic;
    private ChessBoardView chessBoardView;
    private ChessController chessController;

    // Common positions used in tests
    private final ChessboardPosition D2 = new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO);
    private final ChessboardPosition D4 = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR);

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

        when(gameLogic.isValidMove(D2, D4)).thenReturn(false);

        chessController.handleSquareClick(D4);

        verify(chessBoardView).clearHighlights();
    }

    @Test
    public void secondClickValidMoveUnhighlightSelectionTest() {
        when(gameLogic.isMovable(D2)).thenReturn(true);

        chessController.handleSquareClick(D2);

        when(gameLogic.isValidMove(D2, D4)).thenReturn(true);

        chessController.handleSquareClick(D4);

        verify(chessBoardView).clearHighlights();
    }
}