package com.sdm.units.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JButton;
import java.awt.Component;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChessBoardViewTest {
    private ChessBoardView view;
    private ChessController mockController;

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
}