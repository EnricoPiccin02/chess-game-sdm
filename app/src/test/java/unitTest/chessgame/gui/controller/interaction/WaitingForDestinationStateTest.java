package unittest.chessgame.gui.controller.interaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.function.Consumer;

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
import com.sdm.units.chessgame.gui.controller.interaction.InteractionState;
import com.sdm.units.chessgame.gui.controller.interaction.WaitingForDestinationState;
import com.sdm.units.chessgame.gui.controller.interaction.WaitingForSelectionState;

@DisplayName("WaitingForDestinationState")
class WaitingForDestinationStateTest {

    private MoveQuery moveQuery;
    private GameStateController controller;
    private ChessboardInteractionStrategy boardUI;
    private Consumer<InteractionState> transition;
    private WaitingForDestinationState state;
    private ChessboardPosition from;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        moveQuery = mock(MoveQuery.class);
        controller = mock(GameStateController.class);
        boardUI = mock(ChessboardInteractionStrategy.class);
        transition = mock(Consumer.class);
        from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        state = new WaitingForDestinationState(moveQuery, controller, from, transition, boardUI);
    }

    @Nested
    @DisplayName("on enter")
    class OnEnter {

        @Test
        @DisplayName("should clear previous highlights")
        void shouldClearPreviousHighlights() {
            state.onEnter();

            verify(boardUI).clear();
        }

        @Test
        @DisplayName("should highlight available moves")
        void shouldHighlightAvailableMoves() {
            Set<ChessboardPosition> moves = Set.of(
                new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO)
            );
            when(moveQuery.legalDestinations(from)).thenReturn(moves);

            state.onEnter();

            verify(boardUI).enableLegalDestinations(moves);
        }
    }

    @Nested
    @DisplayName("when square clicked")
    class OnSquareClicked {

        @Test
        @DisplayName("should transition back when clicking origin again")
        void shouldTransitionBackWhenClickingOriginAgain() {
            state.onSquareClicked(from);

            verify(transition).accept(any(WaitingForSelectionState.class));
        }

        @Test
        @DisplayName("should not ask for moves again when clicking origin")
        void shouldNotAskForMovesWhenClickingOrigin() {
            state.onSquareClicked(from);

            verifyNoInteractions(moveQuery);
        }

        @Test
        @DisplayName("should not request move execution when clicking origin")
        void shouldNotRequestMoveExecutionWhenClickingOrigin() {
            state.onSquareClicked(from);

            verifyNoInteractions(controller);
        }

        @Test
        @DisplayName("should execute move when destination is chosen")
        void shouldExecuteMoveWhenDestinationIsChosen() {
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);

            state.onSquareClicked(to);

            verify(controller).makeMove(from, to);
        }

        @Test
        @DisplayName("should transition to selection state after move")
        void shouldTransitionToSelectionStateAfterMove() {
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);

            state.onSquareClicked(to);

            verify(transition).accept(any(WaitingForSelectionState.class));
        }
    }
}