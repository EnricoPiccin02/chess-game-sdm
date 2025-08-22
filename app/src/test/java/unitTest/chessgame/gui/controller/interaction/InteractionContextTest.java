package unittest.chessgame.gui.controller.interaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardInteractionStrategy;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionState;
import com.sdm.units.chessgame.gui.controller.interaction.WaitingForSelectionState;

@DisplayName("InteractionContext")
class InteractionContextTest {

    private MoveQuery moveQuery;
    private GameStateController controller;
    private ChessboardInteractionStrategy boardUI;
    private InteractionContext context;

    @BeforeEach
    void setUp() {
        moveQuery = mock(MoveQuery.class);
        controller = mock(GameStateController.class);
        boardUI = mock(ChessboardInteractionStrategy.class);
        context = new InteractionContext(moveQuery, controller, boardUI);
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
}