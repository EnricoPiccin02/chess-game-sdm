package unitTest.chessgame.gui.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.model.domain.ChessGameController;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.controller.command.EndGameCommand;
import com.sdm.units.chessgame.gui.controller.command.GameCommand;
import com.sdm.units.chessgame.gui.controller.command.RestartCommand;
import com.sdm.units.chessgame.gui.controller.command.UndoCommand;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardInteractionStrategy;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionState;
import com.sdm.units.chessgame.gui.controller.interaction.WaitingForSelectionState;

@DisplayName("InteractionContext")
class InteractionContextTest {

    private ChessGameController player;
    private ChessboardInteractionStrategy boardUI;
    private InteractionContext context;

    @BeforeEach
    void setUp() {
        player = mock(ChessGameController.class);
        boardUI = mock(ChessboardInteractionStrategy.class);
        context = new InteractionContext(player, boardUI);
    }

    @Nested
    @DisplayName("state management")
    class StateManagement {

        @Test
        @DisplayName("should delegate square clicks to current state")
        void shouldDelegateSquareClicksToCurrentState() {
            InteractionState state = mock(InteractionState.class);
            context.setState(state);

            ChessboardPosition pos = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
            context.handleSquareClick(pos);

            verify(state).onSquareClicked(pos);
        }

        @Test
        @DisplayName("should reset to WaitingForSelectionState")
        void shouldResetToWaitingForSelectionState() {
            context.resetToStartState();

            assertThat(context)
                .extracting("currentState")
                .isInstanceOf(WaitingForSelectionState.class);
        }
    }

    @Nested
    @DisplayName("command execution")
    class CommandExecution {

        @Test
        @DisplayName("should execute undo command")
        void shouldExecuteUndoCommand() {
            GameCommand cmd = mock(GameCommand.class);

            context.executeCommand(cmd);

            verify(cmd).execute();
        }

        @Test
        @DisplayName("should create proper commands")
        void shouldCreateProperCommands() {
            assertThat(context.createUndoCommand()).isInstanceOf(UndoCommand.class);
            assertThat(context.createRestartCommand()).isInstanceOf(RestartCommand.class);
            assertThat(context.createEndGameCommand()).isInstanceOf(EndGameCommand.class);
        }
    }
}