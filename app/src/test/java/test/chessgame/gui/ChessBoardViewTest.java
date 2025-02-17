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
    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);

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

    @Test
    void shouldHaveCorrectAlternatingSquareColors() {
        // Test some key positions to verify the alternating pattern
        // A1 should be light (rank 0, file 0)
        assertEquals(LIGHT_SQUARE_COLOR, getSquareAt(ChessboardFile.A, ChessboardRank.ONE).getBackground());

        // B1 should be dark (rank 0, file 1)
        assertEquals(DARK_SQUARE_COLOR, getSquareAt(ChessboardFile.B, ChessboardRank.ONE).getBackground());

        // A2 should be dark (rank 1, file 0)
        assertEquals(DARK_SQUARE_COLOR, getSquareAt(ChessboardFile.A, ChessboardRank.TWO).getBackground());

        // B2 should be light (rank 1, file 1)
        assertEquals(LIGHT_SQUARE_COLOR, getSquareAt(ChessboardFile.B, ChessboardRank.TWO).getBackground());

        // H8 should be dark (rank 7, file 7)
        assertEquals(LIGHT_SQUARE_COLOR, getSquareAt(ChessboardFile.H, ChessboardRank.EIGHT).getBackground());
    }

    @Test
    void shouldClearHighlights() {
        // Highlight a square first
        ChessboardPosition A1 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        view.highlightSquare(A1);

        // Clear highlights
        view.clearHighlights();

        // Verify the square returns to its original color (LIGHT_SQUARE_COLOR)
        JButton button = (JButton) view.getComponents()[56];
        assertEquals(LIGHT_SQUARE_COLOR, button.getBackground());
    }

    private long countButtons(Component[] components) {
        return java.util.Arrays.stream(components)
                .filter(component -> component instanceof JButton)
                .count();
    }

    private JButton getSquareAt(ChessboardFile file, ChessboardRank rank) {
        return view.getSquareButton(new ChessboardPosition(file, rank));
    }
}