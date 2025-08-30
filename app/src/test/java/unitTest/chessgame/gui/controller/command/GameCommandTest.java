package unittest.chessgame.gui.controller.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gui.controller.command.EndGameCommand;
import com.sdm.units.chessgame.gui.controller.command.GameCommand;
import com.sdm.units.chessgame.gui.controller.command.RestartCommand;
import com.sdm.units.chessgame.gui.controller.command.UndoCommand;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;

@DisplayName("GameCommand")
class GameCommandTest {

    private InteractionContext context;
    private GameStateController controller;
    private GameCommand command;

    @BeforeEach
    void setUp() {
        context = mock(InteractionContext.class);
        controller = mock(GameStateController.class);
    }

    @Nested
    @DisplayName("when undo command is executed")
    class WhenUndoCommand {

        @BeforeEach
        void setUp() {
            command = new UndoCommand(context, controller);
        }

        @Test
        @DisplayName("should undo last move")
        void shouldUndoLastMoveWhenExecuted() {
            command.execute();

            verify(controller).undoMove();
        }

        @Test
        @DisplayName("should reinitialize context")
        void shouldReinitializeContextWhenExecuted() {
            command.execute();

            verify(context).initialize();
        }
    }

    @Nested
    @DisplayName("when restart command is executed")
    class WhenRestartCommand {

        @BeforeEach
        void setUp() {
            command = new RestartCommand(context, controller);
        }

        @Test
        @DisplayName("should start a new game")
        void shouldStartNewGameWhenExecuted() {
            command.execute();

            verify(controller).start();
        }

        @Test
        @DisplayName("should reinitialize context")
        void shouldReinitializeContextWhenExecuted() {
            command.execute();

            verify(context).initialize();
        }
    }

    @Nested
    @DisplayName("when end game command is executed")
    class WhenEndGameCommand {

        @BeforeEach
        void setUp() {
            command = new EndGameCommand(controller);
        }

        @Test
        @DisplayName("should end the game")
        void shouldEndGameWhenExecuted() {
            command.execute();

            verify(controller).end();
        }
    }
}
