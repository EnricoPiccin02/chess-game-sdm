package test.chessgame.gui.controller;

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

import com.sdm.units.chessgame.gamecontrol.model.domain.ChessGameController;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardInteractionStrategy;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionState;
import com.sdm.units.chessgame.gui.controller.interaction.WaitingForDestinationState;
import com.sdm.units.chessgame.gui.controller.interaction.WaitingForSelectionState;

@DisplayName("WaitingForDestinationState")
class WaitingForDestinationStateTest {

    private ChessGameController player;
    private ChessboardInteractionStrategy boardUI;
    private Consumer<InteractionState> transition;
    private WaitingForDestinationState state;
    private ChessboardPosition from;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        player = mock(ChessGameController.class);
        boardUI = mock(ChessboardInteractionStrategy.class);
        transition = mock(Consumer.class);
        from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        state = new WaitingForDestinationState(player, from, transition, boardUI);
    }

    @Test
    @DisplayName("should enable legal destinations on enter")
    void shouldEnableLegalDestinationsOnEnter() {
        Set<ChessboardPosition> moves = Set.of(
            new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO)
        );
        when(player.getLegalDestinationsFrom(from)).thenReturn(moves);

        state.onEnter();

        verify(boardUI).clear();
        verify(boardUI).enableLegalDestinations(moves);
    }

    @Nested
    @DisplayName("when square clicked")
    class OnSquareClicked {

        @Test
        @DisplayName("should transition back if same square clicked")
        void shouldTransitionBackIfSameSquareClicked() {
            state.onSquareClicked(from);

            verify(transition).accept(any(WaitingForSelectionState.class));
            verifyNoInteractions(player);
        }

        @Test
        @DisplayName("should make move and transition to WaitingForSelectionState")
        void shouldMakeMoveAndTransition() {
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);

            state.onSquareClicked(to);

            verify(player).makeMove(from, to);
            verify(transition).accept(any(WaitingForSelectionState.class));
        }
    }
}