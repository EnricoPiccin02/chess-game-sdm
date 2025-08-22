package unittest.chessgame.gui.controller.interaction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.controller.command.CommandFactory;
import com.sdm.units.chessgame.gui.controller.command.GameCommand;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionController;

@DisplayName("ToolbarInteractionController")
class ToolbarInteractionControllerTest {

    private GameCommand cmd;
    private CommandFactory commandFactory;
    private ToolbarInteractionController toolbar;

    @BeforeEach
    void setUp() {
        cmd = mock(GameCommand.class);
        commandFactory = mock(CommandFactory.class);
        toolbar = new ToolbarInteractionController(commandFactory);
    }

    @Test
    @DisplayName("should delegate undo command creation and then execute it")
    void shouldDelegateUndoAndExecute() {
        when(commandFactory.createUndoCommand()).thenReturn(cmd);

        toolbar.undoLastMove();

        verify(cmd).execute();
    }

    @Test
    @DisplayName("should delegate restart command creation and then execute it")
    void shouldDelegateRestartAndExecute() {
        when(commandFactory.createRestartCommand()).thenReturn(cmd);

        toolbar.restart();

        verify(cmd).execute();
    }

    @Test
    @DisplayName("should delegate close command creation and then execute it")
    void shouldDelegateCloseAndExecute() {
        when(commandFactory.createEndGameCommand()).thenReturn(cmd);

        toolbar.close();

        verify(cmd).execute();
    }   
}