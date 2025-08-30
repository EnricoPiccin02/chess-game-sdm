package unittest.chessgame.gui.controller.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gui.controller.command.CommandFactory;
import com.sdm.units.chessgame.gui.controller.command.EndGameCommand;
import com.sdm.units.chessgame.gui.controller.command.GameCommand;
import com.sdm.units.chessgame.gui.controller.command.RestartCommand;
import com.sdm.units.chessgame.gui.controller.command.UndoCommand;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;

@DisplayName("CommandFactory")
class CommandFactoryTest {

    private InteractionContext context;
    private GameStateController controller;
    private CommandFactory commandFactory;

    @BeforeEach
    void setUp() {
        context = mock(InteractionContext.class);
        controller = mock(GameStateController.class);
        commandFactory = new CommandFactory(context, controller);
    }

    @Test
    @DisplayName("should create proper undo command")
    void shouldCreateProperUndoCommand() {
        GameCommand command = commandFactory.createUndoCommand();

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(UndoCommand.class);
    }

    @Test
    @DisplayName("should create proper end game command")
    void shouldCreateProperEndGameCommand() {
        GameCommand command = commandFactory.createEndGameCommand();

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(EndGameCommand.class);
    }

    @Test
    @DisplayName("should create proper restart command")
    void shouldCreateProperRestartCommand() {
        GameCommand command = commandFactory.createRestartCommand();

        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(RestartCommand.class);
    }
}