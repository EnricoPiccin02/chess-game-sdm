package guitest.chessgame.board.view;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.view.ChessGameToolbar;

import guitest.chessgame.testdoubles.ToolbarInteractionStrategySpy;

@DisplayName("ChessGameToolbar")
class ChessGameToolbarTest {

    private ToolbarInteractionStrategySpy strategySpy;
    private ChessGameToolbar toolbar;

    @BeforeEach
    void setUp() {
        strategySpy = new ToolbarInteractionStrategySpy();
        toolbar = new ChessGameToolbar(strategySpy);
    }

    @Test
    @DisplayName("should delegate the restart of the game")
    void shouldDelegateTheRestartOfTheGame() {
        toolbar.restart();

        assertTrue(strategySpy.isRestartCalled());
    }

    @Test
    @DisplayName("should delegate the undo of last move")
    void shouldDelegateTheUndoOfLastMove() {
        toolbar.undo();

        assertTrue(strategySpy.isUndoCalled());
    }

    @Test
    @DisplayName("should delegate the closing of the game")
    void shouldDelegateTheClosingOfTheGame() {
        toolbar.exit();

        assertTrue(strategySpy.isExitCalled());
    }
}