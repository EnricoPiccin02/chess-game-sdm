package test.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.GameLogic;
import com.sdm.units.chessgame.gui.ChessBoardView;
import com.sdm.units.chessgame.gui.ChessController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ChessControllerTest {

    private GameLogic gameLogic;
    private ChessBoardView chessBoardView;
    private ChessController chessController;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        gameLogic = mock(GameLogic.class);
        chessBoardView = mock(ChessBoardView.class);

        // Initialize the object under test
        chessController = new ChessController(gameLogic, chessBoardView);
    }

    @Test
    void constructorAssignGameLogic() {
        assertEquals(gameLogic, chessController.getGameLogic());
    }

    @Test
    void constructorAssignChessBoardView() {
        assertEquals(chessBoardView, chessController.getChessBoardView());
    }
}
