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

@DisplayName("WaitingForSelectionState")
class WaitingForSelectionStateTest {

    private MoveQuery moveQuery;
    private GameStateController controller;
    private ChessboardInteractionStrategy boardUI;
    private Consumer<InteractionState> transition;
    private WaitingForSelectionState state;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        moveQuery = mock(MoveQuery.class);
        controller = mock(GameStateController.class);
        boardUI = mock(ChessboardInteractionStrategy.class);
        transition = mock(Consumer.class);
        state = new WaitingForSelectionState(moveQuery, controller, transition, boardUI);
    }

    @Test
    @DisplayName("should enable selectable pieces on enter")
    void shouldEnableSelectablePiecesOnEnter() {
        Set<ChessboardPosition> positions = Set.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE)
        );
        when(moveQuery.selectable()).thenReturn(positions);

        state.onEnter();

        verify(boardUI).clear();
        verify(boardUI).enableSelectablePieces(positions);
    }

    @Nested
    @DisplayName("when square clicked")
    class OnSquareClicked {

        @Test
        @DisplayName("should transition to WaitingForDestinationState if legal moves exist")
        void shouldTransitionIfLegalMovesExist() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
            when(moveQuery.legalDestinations(from))
                .thenReturn(Set.of(
                    new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO)
                ));

            state.onSquareClicked(from);

            verify(transition).accept(any(WaitingForDestinationState.class));
        }

        @Test
        @DisplayName("should stay in current state if no legal moves")
        void shouldStayIfNoLegalMoves() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
            when(moveQuery.legalDestinations(from)).thenReturn(Set.of());

            state.onSquareClicked(from);

            verifyNoInteractions(transition);
        }
    }
}