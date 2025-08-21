package unitTest.chessgame.gui.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.controller.command.GameCommand;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionController;

@DisplayName("ToolbarInteractionController")
class ToolbarInteractionControllerTest {

    private InteractionContext context;
    private ToolbarInteractionController toolbar;

    @BeforeEach
    void setUp() {
        context = mock(InteractionContext.class);
        toolbar = new ToolbarInteractionController(context);
    }

    @Test
    @DisplayName("should delegate undo button to context undo command")
    void shouldDelegateUndo() {
        GameCommand cmd = mock(GameCommand.class);
        when(context.createUndoCommand()).thenReturn(cmd);

        toolbar.undoLastMove();

        verify(context).executeCommand(cmd);
    }

    @Test
    @DisplayName("should delegate restart button to context restart command")
    void shouldDelegateRestart() {
        GameCommand cmd = mock(GameCommand.class);
        when(context.createRestartCommand()).thenReturn(cmd);

        toolbar.restart();

        verify(context).executeCommand(cmd);
    }

    @Test
    @DisplayName("should delegate close button to context end game command")
    void shouldDelegateClose() {
        GameCommand cmd = mock(GameCommand.class);
        when(context.createEndGameCommand()).thenReturn(cmd);

        toolbar.close();

        verify(context).executeCommand(cmd);
    }
}