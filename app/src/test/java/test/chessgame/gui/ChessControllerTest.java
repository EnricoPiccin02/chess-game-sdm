package test.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
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
        verify(chessBoardView).unhighlightSquare(ChessboardFile.D, ChessboardRank.TWO);
    }



}


