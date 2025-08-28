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
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionStrategy;

@DisplayName("ToolbarInteractionController")
class ToolbarInteractionControllerTest {

    private GameCommand cmd;
    private CommandFactory commandFactory;
    private ToolbarInteractionStrategy toolbar;

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

        toolbar.undo();

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
    @DisplayName("should delegate exit command creation and then execute it")
    void shouldDelegateExitAndExecute() {
        when(commandFactory.createEndGameCommand()).thenReturn(cmd);

        toolbar.exit();

        verify(cmd).execute();
    }   
}