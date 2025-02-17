package test.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.gui.ChessBoardView;
import com.sdm.units.chessgame.gui.ChessController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JButton;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChessBoardViewTest {
    private ChessBoardView view;
    private ChessController mockController;
    private static final Color HIGHLIGHT_COLOR = new Color(255, 255, 0, 128);

    @BeforeEach
    void setUp() {
        mockController = mock(ChessController.class);
        view = new ChessBoardView(mockController);

    }

    @Test
    void shouldCreateBoardWithCorrectNumberOfSquares() {
        // A chess board should have 64 squares (8x8)
        Component[] components = view.getComponents();
        long buttonCount = countButtons(components);
        assertEquals(64, buttonCount);
    }

    private long countButtons(Component[] components) {
        return java.util.Arrays.stream(components)
                .filter(component -> component instanceof JButton)
                .count();
    }

    @Test
    void shouldCallControllerWhenSquareIsClicked() {
        // Get the button at position A1 (0,7 in the grid)
        JButton buttonA1 = (JButton) view.getComponents()[56]; // 7 * 8 + 0 = 56
        buttonA1.doClick();

        // Verify that the controller was called with position A1
        verify(mockController).handleSquareClick(
                new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE)
        );
    }

    @Test
    void shouldHighlightSquareWhenRequested() {
        ChessboardPosition A1 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        view.highlightSquare(A1);

        JButton highlightedButton = (JButton) view.getComponents()[56];
        assertEquals(HIGHLIGHT_COLOR, highlightedButton.getBackground());
    }
}